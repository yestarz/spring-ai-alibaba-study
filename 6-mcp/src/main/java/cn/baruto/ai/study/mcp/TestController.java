package cn.baruto.ai.study.mcp;


import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "请介绍一下你自己") String prompt) {
        return chatClient.prompt(prompt).call().content();
    }
}
