package com.bjtu.backend.controller.Class;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.IO.Result;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.ClassStudent;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.Class.AddClassService;
import com.bjtu.backend.service.impl.Class.AddClassServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/class")
public class AddClassController
{
    final AddClassService addClassService;
    final ClassMapper classMapper;
    final StudentMapper studentMapper;

    public AddClassController(AddClassService addClassService,
                              ClassMapper classMapper,
                              StudentMapper studentMapper)
    {
        this.addClassService = addClassService;
        this.classMapper = classMapper;
        this.studentMapper = studentMapper;
    }

    @PostMapping("/add")
    public Result<?> addClass(@RequestBody Class classInfo)
    {

        Map<String, String> map = addClassService.addClass(classInfo);

        return Result.success(map);
    }
}
