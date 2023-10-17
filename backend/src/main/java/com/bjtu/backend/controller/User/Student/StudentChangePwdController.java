package com.bjtu.backend.controller.User.Student;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentChangePwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/change")
    public Result<Map<String, String>> change(@RequestBody Student student)
    {
        Map<String, String> map = studentChangePwdService.changePWD(student);

        return Result.success(map);
    }


}
