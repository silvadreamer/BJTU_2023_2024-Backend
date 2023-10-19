package com.bjtu.backend.controller.User.Student;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentChangePwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentChangePwdController
{
    final StudentChangePwdService studentChangePwdService;

    public StudentChangePwdController(StudentChangePwdService studentChangePwdService)
    {
        this.studentChangePwdService = studentChangePwdService;
    }

    /**
     * 修改密码
     * @param student 旧密码类
     * @param new_student 新密码类
     * @return Result类
     */
    @PostMapping("/change")
    public Result<Map<String, String>> change(@RequestBody Student student, @RequestParam String old_pwd)
    {
        Map<String, String> map = studentChangePwdService.changePWD(student, old_pwd);

        if(map == null)
        {
            return Result.fail(20001, "密码错误");
        }

        return Result.success(map);
    }


}
