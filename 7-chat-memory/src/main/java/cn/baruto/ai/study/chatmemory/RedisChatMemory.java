package cn.baruto.ai.study.chatmemory;

import cn.baruto.ai.study.chatmemory.jackson.AssistantMessageDeserializer;
import cn.baruto.ai.study.chatmemory.jackson.UserMessageDeserializer;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Getter;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于Redis的聊天记录存储
 */
@Getter
public class RedisChatMemory implements ChatMemory {

    private final String KEY_PREFIX = "chat:memory:";

    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper;

    public RedisChatMemory(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(AssistantMessage.class, new AssistantMessageDeserializer());
        module.addDeserializer(UserMessage.class, new UserMessageDeserializer());

        objectMapper.registerModule(module);
    }


    @Override
    public void add(String conversationId, List<Message> messages) {
        stringRedisTemplate.opsForList().leftPushAll(KEY_PREFIX + conversationId, messages.stream().map(message -> {
            try {
                return objectMapper.writeValueAsString(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        return stringRedisTemplate.opsForList().range(KEY_PREFIX + conversationId, 0, lastN).stream().map(message -> {
            try {
                return objectMapper.readValue(message, Message.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    @Override
    public void clear(String conversationId) {
        stringRedisTemplate.delete(KEY_PREFIX + conversationId);
    }
}
