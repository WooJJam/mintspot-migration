package WooJJam.mintspot.service;

import WooJJam.mintspot.config.ChatGptConfig;
import WooJJam.mintspot.config.RestTemplateConfig;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.dto.ChatMessageDto;
import WooJJam.mintspot.dto.chat.ChatDto;
import WooJJam.mintspot.dto.chat.ChatMessageRequestDto;
import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import WooJJam.mintspot.dto.gpt.ChatRequestMsgDto;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatGptService {

    private final RestTemplateConfig restTemplateConfig;
    private final ChatGptConfig chatGptConfig;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

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

        String message = jsonParseResponseMessage(chatMessageResponse);
        Chat findChat = chatRepository.findById(chatId);
        messageRepository.saveMessage(findChat, message);
        return message;
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

//    public List<ChatMessageDto> listMessage(Long chatId) {
//        List<Chat> messages = messageRepository.listMessage(chatId);
//        List<ChatMessageDto> messageList = messages.stream()
//                .map(ChatMessageDto::new)
//                .collect(Collectors.toList());
//
//
//        return messageList;
//    }

}
