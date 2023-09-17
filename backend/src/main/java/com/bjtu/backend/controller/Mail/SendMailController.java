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

@RequestMapping("/mail")
@RestController
public class SendMailController
{
    final SendMailService sendMailService;

    public SendMailController(SendMailService sendMailService)
    {
        this.sendMailService = sendMailService;
    }
    
    @PostMapping("/send")
    public Result<?> sendMail(@RequestParam String id)
    {
        Map<String, String> map = sendMailService.sendMail(id);

        if(map == null)
        {
            return Result.fail(20001, "发送失败，请稍后再试");
        }

        return Result.success(map);
    }
}
