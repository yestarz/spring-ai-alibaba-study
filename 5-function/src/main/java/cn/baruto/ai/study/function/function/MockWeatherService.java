package cn.baruto.ai.study.function.function;


import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.function.Function;

@Slf4j
public class MockWeatherService implements Function<MockWeatherService.Request, MockWeatherService.Response> {
    public enum Unit {C, F}

    public record Request(String location, Unit unit) {
    }

    public record Response(double temp, Unit unit) {
    }

    public Response apply(Request request) {
        log.info("MockWeatherService apply:{}", request);
        return new Response(40, request.unit);
    }
}
