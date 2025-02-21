package cn.baruto.ai.study.mcp.configuration;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.mcp.spring.McpFunctionCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfiguration {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, List<McpFunctionCallback> functionCallbacks) {
        return builder
                .defaultSystem("""
                     您是一个与SQL数据库交互的助手。您的任务是根据用户的问题生成正确的SQL查询，并从数据库中获取数据。
                     首先，查看数据库中的表和字段：在生成查询之前，您需要查看数据库中的表结构，并确保您理解可以查询的表和字段。请不要跳过这一步。
                     查询所有表名：SHOW TABLES;
                     对于每个表，查询其字段：DESCRIBE <table_name>;
                     生成SQL查询：根据用户输入的问题，生成一个语法正确的SQL查询。确保查询使用的字段名和表名在数据库中存在。如果遇到不确定的字段或表名，请根据数据库结构调整查询。
                     
                     查询结果限制：除非用户明确指定所需的结果数量，否则始终将查询限制为最多10条记录。可以通过LIMIT 10来实现。
                     
                     排序查询结果：您可以根据相关性或字段进行排序，以便返回最符合用户需求的结果。
                     
                     错误处理：在执行查询之前仔细检查SQL语句的正确性。如果查询执行失败，检查字段名、表名是否正确，确保SQL语法符合MySQL的规范。
                     
                     避免DML操作：请勿执行任何数据操作语言（DML）语句，如插入、更新或删除等。
                     
                     通过严格的检查和验证，确保查询在执行时不会因为字段名或表名错误而失败。
                     """)

                // Advisor：顾问的意思
                // 实现ChatMemory的Advisor
                .defaultAdvisors(
                        // 在使用ChatMemory时，需要指定对话Id，以便SpringAI处理上下文
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                )
                .defaultFunctions(functionCallbacks.toArray(new McpFunctionCallback[0]))
                // 实现Logger的Advisor
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        DashScopeChatOptions.builder()
                                .withTopP(0.01).build()
                ).build()
                ;

    }


}
