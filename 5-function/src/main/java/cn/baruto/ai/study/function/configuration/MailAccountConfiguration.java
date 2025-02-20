package cn.baruto.ai.study.function.configuration;

import cn.baruto.ai.study.function.properties.MailProperties;
import cn.hutool.extra.mail.MailAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailAccountConfiguration {

    @Bean
    public MailAccount mailAccount(MailProperties properties){
        MailAccount account = new MailAccount();
        account.setHost(properties.getHost());
        account.setPort(properties.getPort());
        account.setAuth(true);
        account.setFrom(properties.getFrom());
        account.setUser(properties.getUsername());
        account.setPass(properties.getPassword());
        account.setSslEnable(true);
        return account;
    }

}
