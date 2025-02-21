package cn.baruto.ai.study.structured_output.configuration;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("你是一个乐于助人的智能聊天助手，请根据用户的问题给出简洁明了的回答。")

                // Advisor：顾问的意思
                // 实现ChatMemory的Advisor
                .defaultAdvisors(
                        // 在使用ChatMemory时，需要指定对话Id，以便SpringAI处理上下文
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                )
                // 实现Logger的Advisor
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        DashScopeChatOptions.builder()
                                .withTopP(0.7).build()
                ).build()
                ;

    }


}
