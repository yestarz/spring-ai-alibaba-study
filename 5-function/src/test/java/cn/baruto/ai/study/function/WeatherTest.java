package cn.baruto.ai.study.function;

import cn.baruto.ai.study.function.function.WeatherService;
import cn.baruto.ai.study.function.function.request.WeatherFunctionRequest;
import cn.baruto.ai.study.function.function.request.WeatherFunctionResponse;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherTest {

    @Resource
    private WeatherService weatherService;

    @Test
    public void getWeather(){
        WeatherFunctionRequest request = new WeatherFunctionRequest();
        request.setCity("重庆");
        WeatherFunctionResponse response = weatherService.getWeather(request);
        System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
    }

}
