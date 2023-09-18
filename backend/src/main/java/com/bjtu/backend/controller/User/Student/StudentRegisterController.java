package com.bjtu.backend.controller.User.Student;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.Mail.CheckService;
import com.bjtu.backend.service.User.Student.StudentRegisterService;
import com.bjtu.backend.service.impl.Mail.CheckServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * controller for student register service
 */
@RestController
@RequestMapping("/student")
public class StudentRegisterController
{

    final StudentRegisterService studentRegisterService;

    final CheckService checkService;

    public StudentRegisterController(StudentRegisterService studentRegisterService,
                                     CheckService checkService)
    {
        this.studentRegisterService = studentRegisterService;
        this.checkService = checkService;
    }

    /**
     * 注册学生账号
     * @param student 注册信息
     * @return Result
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody Student student, @RequestParam String code)
    {

        if(!checkService.checkCode(student.getNumber(), code))
        {
            return Result.fail(20001, "验证码错误");
        }

        Map<String, String> map =  studentRegisterService.register(student);

        if(map == null)
        {
            return Result.fail(20001, "注册失败");
        }

        return Result.success(map);
    }
}

