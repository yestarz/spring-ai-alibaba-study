package cn.baruto.ai.study.function;


import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/hello")
    public String hello(String prompt) {
        return chatClient.prompt(prompt).call().content();
    }
}
