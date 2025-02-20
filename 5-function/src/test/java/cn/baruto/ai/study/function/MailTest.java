package cn.baruto.ai.study.function;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailTest {

    @Resource
    private MailAccount mailAccount;

    @Test
    public void send(){
        MailUtil.send(mailAccount, CollUtil.newArrayList("94xinkong@gmail.com"), "测试", "邮件来自Hutool测试", false);
    }

}
