package WooJJam.mintspot.service;

import WooJJam.mintspot.config.ChatGptConfig;
import WooJJam.mintspot.config.RestTemplateConfig;
import WooJJam.mintspot.domain.Bot;
import WooJJam.mintspot.domain.Message;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.dto.BotMessageDto;
import WooJJam.mintspot.dto.chat.ChatMessageRequestDto;
import WooJJam.mintspot.dto.chat.RedisChatDto;
import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import WooJJam.mintspot.dto.gpt.ChatRequestMsgDto;
import WooJJam.mintspot.repository.BotRepository;
import WooJJam.mintspot.repository.ChatRepository;
import WooJJam.mintspot.repository.DataChatRepository;
import WooJJam.mintspot.repository.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatGptService {

    private final RestTemplateConfig restTemplateConfig;
    private final ChatGptConfig chatGptConfig;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final BotRepository botRepository;
    private final DataChatRepository dataChatRepository;
    private final RedisTemplate<String, List<RedisChatDto>> redisTemplate;
    @Transactional
    public BotMessageDto sendMessage(Long chatId, ChatMessageRequestDto chatMessageRequestDto) throws JsonProcessingException, ParseException {

        HttpHeaders headers = chatGptConfig.buildMessageHeader();
        String systemMessage = chatGptConfig.getSystemMessage(chatMessageRequestDto.getGender(), chatMessageRequestDto.getCategory());

        String assistantMessage = cacheNewestListChatMessage(chatId);
        String userMessage = chatMessageRequestDto.getUserContent();
        ChatCompletionDto chatCompletionDto = createRequestMessages(userMessage, assistantMessage, systemMessage);

        HttpEntity<ChatCompletionDto> messageRequestEntity = chatGptConfig.buildMessageBody(chatCompletionDto, headers);

        ResponseEntity<String> chatMessageResponse = restTemplateConfig
                .restTemplate()
                .exchange(
                        chatGptConfig.GPT_MESSAGE_URL,
                        HttpMethod.POST,
                        messageRequestEntity,
                        String.class
                );

        String botMessage = jsonParseResponseMessage(chatMessageResponse);

        Chat findChat = chatRepository.findById(chatId);
        Bot savedBot = botRepository.saveBotMessage(findChat, botMessage);

        log.info("User Message = {}", chatCompletionDto);
        log.info("Chat Message = {}", chatMessageResponse);

        Message savedMessage = messageRepository.saveMessage(findChat, chatMessageRequestDto.getUserContent());
        return new BotMessageDto(savedMessage.getContent(), savedBot.getContent(), savedMessage.getCreatedAt());
    }

    private String jsonParseResponseMessage(ResponseEntity<String> chatMessageResponse) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(chatMessageResponse.getBody());
        JSONArray choices = (JSONArray) jsonObject.get("choices");
        JSONObject choicesBody = (JSONObject) choices.get(0);
        JSONObject message = (JSONObject) choicesBody.get("message");
        return String.valueOf(message.get("content"));
    }

    private ChatCompletionDto createRequestMessages(String userMessage, String assistantMessage, String systemMessage) {
        ChatRequestMsgDto systemRequestMsgDto = new ChatRequestMsgDto(chatGptConfig.systemRole, systemMessage);
        ChatRequestMsgDto assistantRequestMsgDto = new ChatRequestMsgDto(chatGptConfig.assistantRole, assistantMessage);
        ChatRequestMsgDto userRequestMsgDto = new ChatRequestMsgDto(chatGptConfig.userRole,  userMessage);
        List<ChatRequestMsgDto> messages = List.of(systemRequestMsgDto, assistantRequestMsgDto, userRequestMsgDto);
        return new ChatCompletionDto(chatGptConfig.model, messages);
    }

    public List<RedisChatDto> listMessage(Long chatId) {
        Optional<Chat> findChat = dataChatRepository.findById(chatId);
        if (findChat.isPresent()) {
            List<Message> userMessages = findChat.get().getMessages();
            List<Bot> botMessages = findChat.get().getBot();

            List<RedisChatDto> chatMessages = IntStream.range(0, Math.min(userMessages.size(), botMessages.size()))
                    .mapToObj(i -> {
                        Message userMessage = userMessages.get(i);
                        Bot botMessage = botMessages.get(i);
                        return new RedisChatDto(i, userMessage.getChat().getTitle(), userMessage.getContent(), botMessage.getContent());
                    }).collect(Collectors.toList());
            redisTemplate.opsForValue().set("chat::" + chatId, chatMessages, Duration.ofMinutes(30L));
            return chatMessages;
        } else {
            return null;
        }
    }

    public String newestListChatMessage(Long chatId) {
        List<Message> userMessages = this.chatRepository.newestListUserMessage(chatId);
        List<Bot> botMessages = this.chatRepository.newestListBotMessage(chatId);

        return IntStream.range(0, Math.min(userMessages.size(), 5))
                .mapToObj(i -> {
                    Message message = userMessages.get(i);
                    Bot bot = botMessages.get(i);
                    return "USER: " + message.getContent() + "\nCHAT BOT: " + bot.getContent();
                }).collect(Collectors.joining("\n"));
    }

    public String cacheNewestListChatMessage(Long chatId) {

        if (Boolean.TRUE.equals(redisTemplate.hasKey("chat::" + chatId))) {
            List<RedisChatDto> redisChatDtos = redisTemplate.opsForValue().get("chat::" + chatId);
            if (redisChatDtos != null) {
                Collections.reverse(redisChatDtos);
                return redisChatDtos.stream().limit(5).map(redisChatDto -> "USER: " + redisChatDto.getUserMessage() + "\nCHAT BOT: " + redisChatDto.getBotMessage()).collect(Collectors.joining("\n"));
            }
        }
        return "AB";
    }
}
