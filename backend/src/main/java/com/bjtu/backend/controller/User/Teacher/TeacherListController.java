package com.bjtu.backend.controller.User.Teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Teacher.TeacherListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Result<?> getList()
    {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("number", "name");

        Map<String, Object> map = teacherListService.list(queryWrapper);

        return Result.success(map);
    }


}
