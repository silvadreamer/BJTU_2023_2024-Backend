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
    public Result<?> addClass(@RequestParam int classID, @RequestParam int student)
    {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Student> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.eq("id", classID);
        queryWrapper1.eq("id", student);

        Class classInfo = classMapper.selectOne(queryWrapper);
        Student student1 = studentMapper.selectOne(queryWrapper1);

        Map<String, String> map = addClassService.addClass(classInfo, student1);

        if(map.get("status").equals("课程已满"))
        {
            return Result.fail(20001, "课程已满");
        }
        else if (map.get("status").equals("课程已存在"))
        {
            return Result.fail(20001, "重复选择");
        }

        return Result.success("选课成功");
    }
}
