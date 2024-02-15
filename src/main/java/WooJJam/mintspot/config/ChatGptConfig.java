package WooJJam.mintspot.config;

import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class ChatGptConfig {

    @Value("${GPT_API_KEY}")
    private String GPT_API_KEY;
    @Value("${GPT_MESSAGE_URL}")
    public String GPT_MESSAGE_URL;
    public final String model = "gpt-3.5-turbo";

    public HttpHeaders buildMessageHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(GPT_API_KEY);
        return headers;
    }

    public HttpEntity<ChatCompletionDto> buildMessageBody(ChatCompletionDto chatCompletionDto, HttpHeaders headers) {
        return new HttpEntity<>(chatCompletionDto, headers);
    }

}
