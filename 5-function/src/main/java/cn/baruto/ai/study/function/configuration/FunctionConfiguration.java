package cn.baruto.ai.study.function.configuration;

import cn.baruto.ai.study.function.function.EmailService;
import cn.baruto.ai.study.function.function.MockWeatherService;
import cn.baruto.ai.study.function.function.request.MailFunctionRequest;
import cn.baruto.ai.study.function.function.request.MailFunctionResponse;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FunctionConfiguration {

    @Resource
    private EmailService emailService;

    @Bean
    @Description("Get the weather in location") // function description
    public Function<MockWeatherService.Request, MockWeatherService.Response> weatherFunction1() {
        return new MockWeatherService();
    }

    @Bean
    @Description("发送邮件函数") // function description
    public Function<MailFunctionRequest, MailFunctionResponse> sendEmail() {
        return emailService::sendMail;
    }

}
