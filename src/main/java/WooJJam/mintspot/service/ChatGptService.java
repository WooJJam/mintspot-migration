package WooJJam.mintspot.service;

import WooJJam.mintspot.config.ChatGptConfig;
import WooJJam.mintspot.config.RestTemplateConfig;
import WooJJam.mintspot.domain.Message;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.dto.ChatMessageDto;
import WooJJam.mintspot.dto.chat.ChatDto;
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
import java.util.stream.Stream;

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
    public String sendMessage(Long chatId, ChatMessageRequestDto chatMessageRequestDto) throws JsonProcessingException, ParseException {
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
        botRepository.saveBotMessage(findChat, botMessage);
        messageRepository.saveMessage(findChat, chatMessageRequestDto.getUserContent());
        return botMessage;
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

    public List<ChatMessageDto> listMessage(Long chatId) {
        List<Message> messages = messageRepository.listMessage(chatId);
        List<ChatMessageDto> userMessageList = messages.stream()
                .map(message -> new ChatMessageDto(message.getId(), message.getContent(), message.getCreatedAt())).collect(Collectors.toList());
        return userMessageList;
    }

}
