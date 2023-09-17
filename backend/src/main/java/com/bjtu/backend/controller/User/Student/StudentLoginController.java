package com.bjtu.backend.controller.User.Student;


import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * Controller for student login service
 */
@RestController
@RequestMapping("/student")
public class StudentLoginController
{
    @Autowired
    StudentLoginService studentLoginService;


    /**
     * 学生登录
     * @param student 用户对象
     * @return Result类
     */
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Student student)
    {
        Map<String, String> map = studentLoginService.login(student);

        if(map == null)
        {
            return Result.fail(20001, "账号或密码错误");
        }

        return Result.success(map);
    }
}
