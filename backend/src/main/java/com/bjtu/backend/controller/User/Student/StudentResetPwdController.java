package com.bjtu.backend.controller.User.Student;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.Mail.CheckService;
import com.bjtu.backend.service.User.Student.StudentChangePwdService;
import com.bjtu.backend.service.User.Student.StudentLoginService;
import com.bjtu.backend.service.User.Student.StudentResetPwdService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * Controller for student reset password service
 */
@RestController
@RequestMapping("/student")
public class StudentResetPwdController
{
    final StudentResetPwdService studentResetPwdService;
    final CheckService checkService;

    public StudentResetPwdController(StudentResetPwdService studentResetPwdService, CheckService checkService)
    {
        this.studentResetPwdService = studentResetPwdService;
        this.checkService = checkService;
    }


    /**
     * @param student 学生类
     * @return Result
     */
    @PostMapping("/reset")
    public Result<Map<String, String>> reset(@RequestBody Student student, @RequestParam String code)
    {

        if(!checkService.checkCode(student.getNumber(), code))
        {
            return Result.fail(20001, "验证码错误");
        }

        Map<String, String> map = studentResetPwdService.ResetPwd(student);

        return Result.success(map);
    }
}
