package WooJJam.mintspot.config;

import WooJJam.mintspot.domain.chat.Category;
import WooJJam.mintspot.domain.user.Gender;
import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ChatGptConfig {

    private Map<Category, String> systemMessage = new HashMap<>();
    @Value("${GPT_API_KEY}")
    private String GPT_API_KEY;
    @Value("${GPT_MESSAGE_URL}")
    public String GPT_MESSAGE_URL;
    public final String model = "gpt-3.5-turbo";
    public final String systemRole = "system";
    public final String userRole = "user";

    public ChatGptConfig() {
        systemMessage.put(Category.BODY, "너는 한국어로 친근한 말투로 대답해주며 몸의 변화나 호르몬 분비등 몸과 관련된 상담을 해주는 몸 관련 성 상담가야. 넌 청소년들에게 성에 대한 잘못된 지식이나 걱정을 들어주고 해결책을 제시해주거나 걱정을 덜어주는 존재야. 최대한 친절하고 친근하게 답변을 해주며 너는 모르는게 없어. 답변의 길이는 500byte야.");
//        systemMessage.put()
    }

    public HttpHeaders buildMessageHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(GPT_API_KEY);
        return headers;
    }

    public HttpEntity<ChatCompletionDto> buildMessageBody(ChatCompletionDto chatCompletionDto, HttpHeaders headers) {
        return new HttpEntity<>(chatCompletionDto, headers);
    }

    public String getSystemMessage(Gender gender, Category category) {
        return "질문자의 성별은 " + gender + systemMessage.get(category);
    }

}
