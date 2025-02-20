package cn.baruto.ai.study.function.function;

import cn.baruto.ai.study.function.function.request.MailFunctionRequest;
import cn.baruto.ai.study.function.function.request.MailFunctionResponse;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Resource
    private MailAccount mailAccount;

    public MailFunctionResponse sendMail(MailFunctionRequest request){
        String logContent = "邮件发送请求，收件人：%s,主题：%s,内容：%s".formatted(CollUtil.join(request.getReceiverList(), ","), request.getSubject(), request.getContent());
        log.info(logContent);
        String messageId = MailUtil.send(mailAccount,request.getReceiverList(), request.getSubject(), request.getContent(), false);

        MailFunctionResponse response = new MailFunctionResponse();
        response.setDescription("邮件发送成功，收件人：%s,主题：%s,内容：%s".formatted(CollUtil.join(request.getReceiverList(), ","), request.getSubject(), request.getContent()));
        response.setMessageId(messageId);
        return response;
    }

}
