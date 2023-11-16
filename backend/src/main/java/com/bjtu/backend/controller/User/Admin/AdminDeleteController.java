package com.bjtu.backend.controller.User.Admin;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.User.Admin.AdminDeleteService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminDeleteController
{

    final AdminDeleteService adminDeleteService;

    public AdminDeleteController(AdminDeleteService adminDeleteService)
    {
        this.adminDeleteService = adminDeleteService;
    }


    @PostMapping("/deleteStudent")
    public Result<?> deleteStudent(@RequestParam String number)
    {
        var map = adminDeleteService.deleteStudent(number);

        return Result.success(map);
    }

    @PostMapping("/deleteTeacher")
    public Result<?> deleteTeacher(@RequestParam String number)
    {
        var map = adminDeleteService.deleteTeacher(number);

        return Result.success(map);
    }

}
