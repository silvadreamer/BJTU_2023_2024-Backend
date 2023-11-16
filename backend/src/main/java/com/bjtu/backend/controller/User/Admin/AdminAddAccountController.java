package com.bjtu.backend.controller.User.Admin;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.User.Admin.AdminAddAccountService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminAddAccountController
{
    final AdminAddAccountService adminAddAccountService;

    public AdminAddAccountController(AdminAddAccountService adminAddAccountService)
    {
        this.adminAddAccountService = adminAddAccountService;
    }

    @PostMapping("/addStudent")
    public Result<?> addStudent(@RequestParam String number, @RequestParam String name)
    {
        var map = adminAddAccountService.addStudent(number, name);

        return Result.success(map);
    }

    @PostMapping("/addTeacher")
    public Result<?> addTeacher(@RequestParam String number, @RequestParam String name)
    {
        var map = adminAddAccountService.addTeacher(number, name);

        return Result.success(map);
    }
}
