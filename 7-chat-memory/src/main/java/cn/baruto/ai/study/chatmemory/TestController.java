package cn.baruto.ai.study.chatmemory;


import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Resource
    private ChatClient chatClient;

}
