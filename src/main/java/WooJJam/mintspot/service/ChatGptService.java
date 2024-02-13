package WooJJam.mintspot.service;

import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    @Value("${GPT_API_KEY}")
    private String GPT_API_KEY;
    @Value("${GPT_MESSAGE_URL}")
    private String GPT_MESSAGE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> sendMessage(ChatCompletionDto chatCompletionDto) {
        HttpHeaders headers = buildMessageHeaderRequest();
        HttpEntity<ChatCompletionDto> messageRequestEntity = new HttpEntity<>(chatCompletionDto, headers);
        return restTemplate.exchange(
                GPT_MESSAGE_URL,
                HttpMethod.POST,
                messageRequestEntity,
                String.class
        );
    }

    private HttpHeaders buildMessageHeaderRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(GPT_API_KEY);
        return headers;
    }
}
