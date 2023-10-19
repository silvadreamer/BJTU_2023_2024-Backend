package com.bjtu.backend.controller.Mail;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Mail.SendMailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Silva31
 * @version 1.0
 * @date 2023/9/17 21:46
 */

@RequestMapping("/mail/send")
@RestController
public class SendMailController
{
    final SendMailService sendMailService;

    public SendMailController(SendMailService sendMailService)
    {
        this.sendMailService = sendMailService;
    }

    @PostMapping("/register")
    public Result<?> sendMailForRegister(@RequestParam String id)
    {
        Map<String, String> map = sendMailService.sendMailForRegister(id);

        if(map == null)
        {
            return Result.fail(20001, "发送失败，请稍后再试");
        }
        else if(map.get("status").equals("用户已存在"))
        {
            return Result.fail(20001, "用户已注册");
        }

        return Result.success(map);
    }

    @PostMapping("/reset")
    public Result<?> sendMailForReset(@RequestParam String id)
    {
        Map<String, String> map = sendMailService.sendMailForReset(id);

        if(map == null)
        {
            return Result.fail(20001, "发送失败，请稍后再试");
        }
        else if(map.get("status").equals("用户不存在"))
        {
            return Result.fail(20001, "用户不存在");
        }

        return Result.success(map);
    }
}
