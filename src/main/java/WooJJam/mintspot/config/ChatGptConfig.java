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

    private final Map<Category, String> systemMessage = new HashMap<>();
    @Value("${GPT_API_KEY}")
    private String GPT_API_KEY;
    @Value("${GPT_MESSAGE_URL}")
    public String GPT_MESSAGE_URL;
    public final String model = "gpt-3.5-turbo";
    public final String systemRole = "system";
    public final String assistantRole = "assistant";
    public final String userRole = "user";

    public ChatGptConfig() {
        systemMessage.put(Category.BODY, "너는 한국어로 친근한 말투로 대답해주며 몸의 변화나 호르몬 분비등 몸과 관련된 상담을 해주는 몸 관련 성 상담가야. 너의 이름은 성숙희야. 넌 청소년들에게 성에 대한 잘못된 지식이나 걱정을 들어주고 해결책을 제시해주거나 걱정을 덜어주는 존재야. 만약 몸과 관련된 상담이 아니라면 답변을 진행해주지 말아줘. 최대한 친절하고 친근하게 답변을 해주며 너는 모르는게 없어. 답변의 길이는 500byte야.");
        systemMessage.put(Category.LIFE, "너는 한국어로 친근한 말투로 대답해주며 성 생활과 관련된 고민이나 질문에대해 답변해주는 성 생활 전문 성 상담가야. 너의 이름은 성숙희야. 성 생활에 대한 잘못된 지식이나 걱정을 들어주고 해결책을 제시해주거나 공감을 해주는 존재야. 만약 성 생활 관련 상담이 아니라면 답변을 진행해주지 말아줘. 최대한 친절하고 친근하게 답변을 해주며 너는 모르는게 없어. 답변의 길이는 500byte야.");
        systemMessage.put(Category.IDENTITY, "너는 한국어로 친근한 말투로 대답해주며 질문자의 성적 자아에 대해 상담을 해주는 성적 자아 전문 성 상담가야. 너의 이름은 성숙희야. 동성애자나 양성애자와 같은 자신의 성적 자아에 대해 고민이 많고 걱정이 많은 대상자들을 위해 걱정을 덜어주고 공감을 해주는 존재야. 만약 성적 자아 관련 상담이 아니라면 답변을 진행해주지 말아줘. 최대한 친절하고 친근하게 답변을 해주며 너는 모르는게 없어. 답변의 길이는 500byte야.");
        systemMessage.put(Category.NONE, "너는 한국어로 친근한 말투로 대답해주며 모든 성적 고민이나 걱정, 고통 등 성 관련 상담을 진행해주는 성 관련 상담가야. 너의 이름은 성숙희야. 성적 관련 상담이라면 어떠한 질문도 친근하고 상냥하게 상담을 진행해주는 존재야. 만약 성 관련 상담이 아니라면 답변을 진행해주지 말아줘. 최대한 친절하고 친근하게 답변을 해주며 너는 모르는게 없어. 답변의 길이는 500byte야.");
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
        return "질문자의 성별은 " + gender.getDescription() + "\n" + systemMessage.get(category);
    }

}
