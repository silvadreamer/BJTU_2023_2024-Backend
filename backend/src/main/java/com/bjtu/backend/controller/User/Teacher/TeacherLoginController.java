package com.bjtu.backend.controller.User.Teacher;

/**
 * @author Silva31
 * @version 1.0
 * @date 2023/9/17 20:23
 */

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Teacher.TeacherLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * controller for teacher login service
 */
@RestController
@RequestMapping("/teacher")
public class TeacherLoginController
{
    @Autowired
    TeacherLoginService loginService;


    /**
     * 教师登录
     * @param teacher 用户对象
     * @return Result类
     */
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Teacher teacher)
    {
        Map<String, String> map = loginService.login(teacher);

        if(map == null)
        {
            return Result.fail(20001, "账号或密码错误");
        }

        return Result.success(map);
    }
}
