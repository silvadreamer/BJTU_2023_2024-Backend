package com.bjtu.backend.controller.User.Student;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentRegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * controller for student register service
 */
@RestController
@RequestMapping("/student")
public class StudentRegisterController
{

    final StudentRegisterService studentRegisterService;

    public StudentRegisterController(StudentRegisterService studentRegisterService)
    {
        this.studentRegisterService = studentRegisterService;
    }

    /**
     * 注册学生账号
     * @param student 注册信息
     * @return Result
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody Student student)
    {
        Map<String, String> map =  studentRegisterService.register(student);

        if(map == null)
        {
            return Result.fail(20001, "注册失败");
        }

        return Result.success(map);
    }
}

