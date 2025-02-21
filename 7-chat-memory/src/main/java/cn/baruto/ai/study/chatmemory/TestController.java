package cn.baruto.ai.study.chatmemory;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
public class TestController {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/test")
    public String test(@RequestParam(defaultValue = "请介绍一下你自己") String prompt) {
        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping("/advisor/chat/{id}")
    public Flux<String> advisorChat(
            HttpServletResponse response,
            @PathVariable String id,
            @RequestParam String query) {

        response.setCharacterEncoding("UTF-8");

        return this.chatClient.prompt(query)
                .advisors(
                        a -> a
                                .param(CHAT_MEMORY_CONVERSATION_ID_KEY, id)
                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                ).stream().content();
    }

}
