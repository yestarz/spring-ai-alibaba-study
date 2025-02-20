package cn.baruto.ai.study.chatmodel;


import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Resource
    private ChatModel chatModel;

    @Resource
    private ImageModel imageModel;

    @RequestMapping("/chat")
    public String chat(String input) {
        ChatResponse response = chatModel.call(new Prompt(input));
        return response.getResult().getOutput().getContent();
    }

    @RequestMapping("/image")
    public String image(String input) {
        ImageOptions options = ImageOptionsBuilder.builder()
                .model("wanx2.1-t2i-turbo")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(input, options);
        ImageResponse response = imageModel.call(imagePrompt);
        String imageUrl = response.getResult().getOutput().getUrl();

        return imageUrl;
    }
}
