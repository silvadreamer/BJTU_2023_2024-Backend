package com.bjtu.backend.service.impl.Mail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.config.MyRedisConfig;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.Mail.SendMailService;
import com.bjtu.backend.utils.TimeGenerateUtil;
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


/**
 * 初步设计:仅供学生注册~教师默认有账户
 */
@Service
public class SendMailServiceImpl implements SendMailService
{
    final RedisTemplate redisTemplate;

    final StudentMapper studentMapper;

    @Resource
    private JavaMailSender mailSender;

    //@Value("${spring.mail.username}")
    private String emailUserName = "heyh2003@qq.com";

    public SendMailServiceImpl(RedisTemplate redisTemplate, StudentMapper studentMapper)
    {
        this.redisTemplate = redisTemplate;
        this.studentMapper = studentMapper;
    }

    /**
     * 发送验证码
     * @param id 学号
     * @return map
     */
    @Override
    public Map<String, String> sendMailForRegister(String id)
    {
        System.out.println(TimeGenerateUtil.getTime() + " 注册邮件");

        Map<String, String> map = new HashMap<>();

        //先检查是否已经注册过了
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", id);
        Student student = studentMapper.selectOne(queryWrapper);
        if(student != null)
        {
            map.put("status", "用户已存在！");
            return map;
        }

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

    /**
     * 重设密码
     * @param id 学号
     * @return map
     */
    @Override
    public Map<String, String> sendMailForReset(String id)
    {
        System.out.println(TimeGenerateUtil.getTime() + " 重设密码");

        Map<String, String> map = new HashMap<>();

        //先检查是否已经注册过了
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", id);
        Student student = studentMapper.selectOne(queryWrapper);

        if(student == null)
        {
            map.put("status", "用户不存在！");
            return map;
        }

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
