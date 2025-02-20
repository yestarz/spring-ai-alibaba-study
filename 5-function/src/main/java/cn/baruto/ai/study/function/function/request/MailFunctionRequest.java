package cn.baruto.ai.study.function.function.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailFunctionRequest {

    @JsonProperty(required = true, value = "receiver")
    @JsonPropertyDescription("收件地址，例如: [baruto@163.com, baruto@qq.com]")
    private List<String> receiverList;

    @JsonProperty(required = true, value = "subject")
    @JsonPropertyDescription("邮件主题，例如：重要提醒")
    private String subject;

    @JsonProperty(required = true, value = "content")
    @JsonPropertyDescription("邮件内容，例如：这是一封测试邮件")
    private String content;

}
