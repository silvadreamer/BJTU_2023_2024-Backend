package com.bjtu.backend.controller.User.Teacher;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Teacher.TeacherRegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * controller for teacher register service
 */

@RestController
@RequestMapping("/teacher")
public class TeacherRegisterController
{
    final TeacherRegisterService registerService;

    public TeacherRegisterController(TeacherRegisterService registerService)
    {
        this.registerService = registerService;
    }


    @PostMapping("/register")
    public Result<?> register(@RequestBody Teacher teacher)
    {
        Map<String, String> map = registerService.register(teacher);

        if(map == null)
        {
            return Result.fail(20001, "注册失败");
        }

        return Result.success(map);
    }

}
