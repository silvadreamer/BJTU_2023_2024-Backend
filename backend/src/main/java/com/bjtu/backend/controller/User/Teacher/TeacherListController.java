package com.bjtu.backend.controller.User.Teacher;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.User.Teacher.TeacherListService;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherListController
{
    final TeacherListService teacherListService;

    public TeacherListController(TeacherListService teacherListService)
    {
        this.teacherListService = teacherListService;
    }

    @GetMapping("/list")
    public Result<?> getList(@RequestParam(required = false) String name,
                             @RequestParam(defaultValue = "1") Long pageNo,
                             @RequestParam(defaultValue = "10") Long pageSize)
    {
        var map = teacherListService.list(name, pageNo, pageSize);

        return Result.success(map);
    }


}
