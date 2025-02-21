package cn.baruto.ai.study.document_retriever;


import com.alibaba.cloud.ai.advisor.DocumentRetrievalAdvisor;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Resource
    private ChatClient chatClient;

    @Resource
    private DocumentRetriever documentRetriever;

    @GetMapping(value = "/chat")
    public String generation(String userInput) {
        // 发起聊天请求并处理响应
        return chatClient.prompt()
                .user(userInput)
                .advisors(new DocumentRetrievalAdvisor(documentRetriever))
                .call().content();
    }
}
