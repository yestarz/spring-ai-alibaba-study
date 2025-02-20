package cn.baruto.ai.study.chatclient;


import cn.baruto.ai.study.chatclient.entity.Cat;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class TestController {

    @Resource
    private ChatClient chatClient;

    @Resource
    private ChatClient translateChatClient;

    @GetMapping("/hello")
    public ChatResponse hello1() {
        return chatClient.prompt().user("给我讲个笑话").call().chatResponse();
    }

    @GetMapping("/hello-stream-chat-response")
    public Flux<ChatResponse> hello2(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return chatClient.prompt().user("给我讲个笑话").stream().chatResponse();
    }

    @GetMapping("/hello-stream-string")
    public Flux<String> hello3(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return chatClient.prompt().user("给我讲个笑话").stream().content();
    }

    @GetMapping("/entity1")
    public Cat entity1() {
        return chatClient.prompt().user("给我生成一只小猫的信息，包含名字、年龄和颜色").call().entity(Cat.class);
    }

    @GetMapping("/entity-list")
    public List<Cat> entityList() {
        return chatClient.prompt().user("给我生成10只小猫的信息，包含名字、年龄和颜色").call().entity(new ParameterizedTypeReference<List<Cat>>() {
        });
    }


    @GetMapping("/translate")
    public String translate(String target, String source) {
        return translateChatClient.prompt().system(sp -> sp.param("target", target)).user(source).call().content();
    }

}
