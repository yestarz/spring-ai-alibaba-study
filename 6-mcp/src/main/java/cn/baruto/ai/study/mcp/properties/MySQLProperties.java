package cn.baruto.ai.study.mcp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("mysql")
@Component
public class MySQLProperties {

    private String host;
    private String port;
    private String username;
    private String password;
    private String database;

}
