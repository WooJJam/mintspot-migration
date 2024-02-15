package WooJJam.mintspot.service;

import WooJJam.mintspot.config.ChatGptConfig;
import WooJJam.mintspot.config.RestTemplateConfig;
import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    private final RestTemplateConfig restTemplateConfig;
    private final ChatGptConfig chatGptConfig;

    public ResponseEntity<String> sendMessage(ChatCompletionDto chatCompletionDto) {
        HttpHeaders headers = chatGptConfig.buildMessageHeader();
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

}
