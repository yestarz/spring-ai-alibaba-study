package cn.baruto.ai.study.chatmemory.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.ai.chat.messages.UserMessage;

import java.io.IOException;

public class UserMessageDeserializer extends JsonDeserializer<UserMessage> {

    @Override
    public UserMessage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String content = node.get("content").asText();
        // 你可以在这里根据需要进一步处理其他字段
        return new UserMessage(content);
    }
}
