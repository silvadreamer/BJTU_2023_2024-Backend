package com.bjtu.backend.controller.User.Admin;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.User.Admin.AdminResetPWDService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminResetPWDController
{
    final AdminResetPWDService adminResetPWDService;

    public AdminResetPWDController(AdminResetPWDService adminResetPWDService)
    {
        this.adminResetPWDService = adminResetPWDService;
    }

    @PostMapping("/resetStudent")
    public Result<?> resetStudent(@RequestParam String number, @RequestParam String pwd)
    {
        var map = adminResetPWDService.resetStudent(number, pwd);

        return Result.success(map);
    }

    @PostMapping("/resetTeacher")
    public Result<?> resetTeacher(@RequestParam String number, @RequestParam String pwd)
    {
        var map = adminResetPWDService.resetTeacher(number, pwd);

        return Result.success(map);
    }

}
