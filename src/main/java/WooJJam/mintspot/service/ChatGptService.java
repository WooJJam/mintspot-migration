package WooJJam.mintspot.service;

import WooJJam.mintspot.config.ChatGptConfig;
import WooJJam.mintspot.config.RestTemplateConfig;
import WooJJam.mintspot.domain.Bot;
import WooJJam.mintspot.domain.Message;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.dto.BotMessageDto;
import WooJJam.mintspot.dto.chat.ChatDto;
import WooJJam.mintspot.dto.chat.ChatMessageDto;
import WooJJam.mintspot.dto.chat.ChatMessageRequestDto;
import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import WooJJam.mintspot.dto.gpt.ChatRequestMsgDto;
import WooJJam.mintspot.repository.BotRepository;
import WooJJam.mintspot.repository.ChatRepository;
import WooJJam.mintspot.repository.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatGptService {

    private final RestTemplateConfig restTemplateConfig;
    private final ChatGptConfig chatGptConfig;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final BotRepository botRepository;

    @Transactional
    public BotMessageDto sendMessage(Long chatId, ChatMessageRequestDto chatMessageRequestDto) throws JsonProcessingException, ParseException {
        HttpHeaders headers = chatGptConfig.buildMessageHeader();
        String systemMessage = chatGptConfig.getSystemMessage(chatMessageRequestDto.getGender(), chatMessageRequestDto.getCategory());
        ChatCompletionDto chatCompletionDto = createRequestMessages(chatMessageRequestDto.getUserContent(), systemMessage);
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

    private ChatCompletionDto createRequestMessages(String userMessage, String systemMessage) {
        ChatRequestMsgDto systemRequestMsgDto = new ChatRequestMsgDto(chatGptConfig.systemRole, systemMessage);
        ChatRequestMsgDto userRequestMsgDto = new ChatRequestMsgDto(chatGptConfig.userRole, userMessage);
        List<ChatRequestMsgDto> messages = List.of(systemRequestMsgDto, userRequestMsgDto);
        return new ChatCompletionDto(chatGptConfig.model, messages);
    }

    public List<ChatDto> listMessage(Long chatId) {
        Chat chat = messageRepository.listMessage(chatId);
        List<Message> userMessages = chat.getMessages();
        List<Bot> botMessages = chat.getBot();

        return IntStream.range(0, Math.min(userMessages.size(), botMessages.size()))
                .mapToObj(i -> {
                    Message userMessage = userMessages.get(i);
                    Bot botMessage = botMessages.get(i);
                    return new ChatDto(i, chat.getTitle(), userMessage, botMessage);
                }).collect(Collectors.toList());
    }
}
