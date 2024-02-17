package WooJJam.mintspot.service;

import WooJJam.mintspot.config.ChatGptConfig;
import WooJJam.mintspot.config.RestTemplateConfig;
import WooJJam.mintspot.dto.chat.ChatMessageRequestDto;
import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import WooJJam.mintspot.dto.gpt.ChatRequestMsgDto;
import WooJJam.mintspot.dto.gpt.ChatResponseMsgDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    private final RestTemplateConfig restTemplateConfig;
    private final ChatGptConfig chatGptConfig;

    public Object sendMessage(ChatMessageRequestDto chatMessageRequestDto) throws JsonProcessingException, ParseException {
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

}
