package com.bjtu.backend.service.impl.Mail;

import com.bjtu.backend.config.MyRedisConfig;
import com.bjtu.backend.service.Mail.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.bjtu.backend.utils.VerCodeGenerateUtil.generateVerCode;

@Service
public class SendMailServiceImpl implements SendMailService
{
    final RedisTemplate redisTemplate;

    @Resource
    private JavaMailSender mailSender;

    //@Value("${spring.mail.username}")
    private String emailUserName = "heyh2003@qq.com";

    public SendMailServiceImpl(RedisTemplate redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 发送验证码
     * @param id 学号
     * @return
     */
    @Override
    public Map<String, String> sendMail(String id)
    {
        Map<String, String> map = new HashMap<>();
        String email = id + "@bjtu.edu.cn";
        String title = "请查收验证码";
        try
        {
            String code = generateVerCode();

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(email, code, 5, TimeUnit.MINUTES);

            String body = code + ", 验证码有效期5分钟";

            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

            message.setFrom(emailUserName);
            message.setTo(email);
            message.setSubject(title);
            message.setText(body);

            this.mailSender.send(mimeMessage);
        }
        catch (Exception e)
        {
            return null;
        }

        map.put("status", "发送成功");
        return map;
    }
}
