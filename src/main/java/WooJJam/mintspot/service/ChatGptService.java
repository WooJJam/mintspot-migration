package WooJJam.mintspot.service;

import WooJJam.mintspot.config.ChatGptConfig;
import WooJJam.mintspot.config.RestTemplateConfig;
import WooJJam.mintspot.dto.chat.ChatMessageRequestDto;
import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import WooJJam.mintspot.dto.gpt.ChatRequestMsgDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    private final RestTemplateConfig restTemplateConfig;
    private final ChatGptConfig chatGptConfig;

    public ResponseEntity<String> sendMessage(ChatMessageRequestDto chatMessageRequestDto) {
        HttpHeaders headers = chatGptConfig.buildMessageHeader();
        String systemMessage = chatGptConfig.getSystemMessage(chatMessageRequestDto.getGender(), chatMessageRequestDto.getCategory());
        ChatCompletionDto chatCompletionDto = createRequestMessages(chatMessageRequestDto.getUserContent(), systemMessage);
        HttpEntity<ChatCompletionDto> messageRequestEntity = chatGptConfig.buildMessageBody(chatCompletionDto, headers);
        return restTemplateConfig
                .restTemplate()
                .exchange(
                        chatGptConfig.GPT_MESSAGE_URL,
                        HttpMethod.POST,
                        messageRequestEntity,
                        String.class
        );
    }

    private ChatCompletionDto createRequestMessages(String userMessage, String systemMessage) {
        ChatRequestMsgDto systemRequestMsgDto = new ChatRequestMsgDto(chatGptConfig.systemRole, systemMessage);
        ChatRequestMsgDto userRequestMsgDto = new ChatRequestMsgDto(chatGptConfig.userRole, userMessage);
        List<ChatRequestMsgDto> messages = List.of(systemRequestMsgDto, userRequestMsgDto);
        return new ChatCompletionDto(chatGptConfig.model, messages);
    }

}
