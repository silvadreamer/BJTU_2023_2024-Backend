package com.bjtu.backend.controller.Class;


import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.service.Class.RegisterClassService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/class")
public class RegisterClassController
{

    final RegisterClassService registerClassService;

    public RegisterClassController(RegisterClassService registerClassService)
    {
        this.registerClassService = registerClassService;
    }

    @PostMapping("/register")
    public Result<?> registerClass(@RequestBody Class classInfo)
    {
        registerClassService.registerClass(classInfo);

        return Result.success(20000, "课程注册成功");
    }
}
