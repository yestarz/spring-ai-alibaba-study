package cn.baruto.ai.study.function.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "mail")
@Component
public class MailProperties {

    private String host;
    private int port;
    private String from;
    private String username;
    private String password;

}
