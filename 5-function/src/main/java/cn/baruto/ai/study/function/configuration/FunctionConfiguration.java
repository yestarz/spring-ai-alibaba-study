package cn.baruto.ai.study.function.configuration;

import cn.baruto.ai.study.function.function.EmailService;
import cn.baruto.ai.study.function.function.WeatherService;
import cn.baruto.ai.study.function.function.request.MailFunctionRequest;
import cn.baruto.ai.study.function.function.request.MailFunctionResponse;
import cn.baruto.ai.study.function.function.request.WeatherFunctionRequest;
import cn.baruto.ai.study.function.function.request.WeatherFunctionResponse;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FunctionConfiguration {

    @Resource
    private EmailService emailService;

    @Resource
    private WeatherService weatherService;

    @Bean
    @Description("获取某个城市的天气") // function description
    public Function<WeatherFunctionRequest, WeatherFunctionResponse> weatherFunction() {
        return weatherService::getWeather;
    }

    @Bean
    @Description("发送邮件函数") // function description
    public Function<MailFunctionRequest, MailFunctionResponse> sendEmail() {
        return emailService::sendMail;
    }

}
