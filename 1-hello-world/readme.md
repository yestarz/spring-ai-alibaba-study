在构建ChatClient的时候设置以下参数可以使得对话有记忆：
```java
new MessageChatMemoryAdvisor(new InMemoryChatMemory())
```
