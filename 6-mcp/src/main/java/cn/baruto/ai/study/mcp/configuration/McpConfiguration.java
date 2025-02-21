package cn.baruto.ai.study.mcp.configuration;

import cn.baruto.ai.study.mcp.properties.MySQLProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.ai.mcp.client.McpClient;
import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.client.stdio.ServerParameters;
import org.springframework.ai.mcp.client.stdio.StdioClientTransport;
import org.springframework.ai.mcp.spring.McpFunctionCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class McpConfiguration {

    @Resource
    private MySQLProperties mySQLProperties;

    /**
     * {
     * "mcpServers": {
     * "mcp_server_myqsl": {
     * "command": "npx",
     * "args": [
     * "-y",
     * "@benborla29/mcp-server-mysql",
     * ],
     * "env": {
     * "MYSQL_HOST": "127.0.0.1",
     * "MYSQL_PORT": "3306",
     * "MYSQL_USER": "root",
     * "MYSQL_PASS": "",
     * "MYSQL_DB": "db_name"
     * }
     * <p>
     * }
     * }
     * }
     *
     * @return
     */
    @Bean(destroyMethod = "close")
    public McpSyncClient mysqlMcpClient() {

        HashMap<String, String> env = new HashMap<>();
        env.put("MYSQL_HOST", mySQLProperties.getHost());
        env.put("MYSQL_PORT", mySQLProperties.getPort());
        env.put("MYSQL_USER", mySQLProperties.getUsername());
        env.put("MYSQL_PASS", mySQLProperties.getPassword());
        env.put("MYSQL_DB", mySQLProperties.getDatabase());

        var stdioParams = ServerParameters.builder("D:\\Program Files\\nodejs\\npx.cmd")
                .args("-y", "@benborla29/mcp-server-mysql")
                .env(env)
                .build();

        var mcpClient = McpClient.using(new StdioClientTransport(stdioParams))
                .requestTimeout(Duration.ofSeconds(30)).sync();

        var init = mcpClient.initialize();

        System.out.println("MCP Initialized: " + init);

        return mcpClient;
    }

    @Bean
    public List<McpFunctionCallback> functionCallbacks(McpSyncClient mysqlMcpClient) {
        return mysqlMcpClient.listTools(null)
                .tools()
                .stream()
                .map(tool -> new McpFunctionCallback(mysqlMcpClient, tool))
                .toList();
    }
}
