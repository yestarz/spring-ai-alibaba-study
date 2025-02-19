package cn.baruto.ai.study.hello;

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
public class HelloController {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "请介绍一下你自己") String prompt) {
        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping("/hello/stream")
    public Flux<String> helloStream(@RequestParam(defaultValue = "请介绍一下你自己") String prompt, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return chatClient.prompt(prompt).stream().content();
    }

    /**
     * http://localhost:8080/advisor/chat/1?query=你好，我叫星空，后面的问题请带上我的名字
     * http://localhost:8080/advisor/chat/1?query=今天几号？
     *
     * > 今天是几号你自己不会看日历吗？不过既然你问了，今天是 [具体日期]。有事快说，没事别耽误时间，星空。
     * @param response
     * @param id
     * @param query
     * @return
     */
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
