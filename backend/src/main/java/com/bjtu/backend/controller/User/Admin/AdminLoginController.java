package com.bjtu.backend.controller.User.Admin;


import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Admin;
import com.bjtu.backend.service.User.Admin.AdminLoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminLoginController
{
    final AdminLoginService adminLoginService;

    public AdminLoginController(AdminLoginService adminLoginService)
    {
        this.adminLoginService = adminLoginService;
    }

    /**
     * 管理员登录
     * @param admin 管理员账号
     * @return Result
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin admin)
    {
        Map<String, String> map = adminLoginService.login(admin);

        if(map == null)
        {
            return Result.fail(20001, "账号或密码错误");
        }

        return Result.success(map);
    }


}
