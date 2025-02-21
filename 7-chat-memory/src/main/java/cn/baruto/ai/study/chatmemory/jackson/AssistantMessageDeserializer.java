package cn.baruto.ai.study.chatmemory.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.messages.AssistantMessage;

import java.io.IOException;
import java.util.Map;

/**
 * 只做了最简单的反序列化操作，设置了content和metadata字段
 */
public class AssistantMessageDeserializer extends JsonDeserializer<AssistantMessage> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AssistantMessage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String content = node.get("content").asText();
        JsonNode metadataNode = node.get("metadata");
        Map<String, Object> metadata = null;
        if (metadataNode != null) {
            // 将 metadata 节点转换为 Map<String, Object>
            metadata = objectMapper.convertValue(metadataNode, new TypeReference<Map<String, Object>>() {});
        }


        // 你可以在这里根据需要进一步处理其他字段
        return new AssistantMessage(content, metadata);
    }
}
