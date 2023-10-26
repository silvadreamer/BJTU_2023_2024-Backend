package com.bjtu.backend.service.impl.Class;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.mapper.ClassStudentMapper;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.ClassStudent;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.Class.AddClassService;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddClassServiceImpl implements AddClassService
{
    final TeacherMapper teacherMapper;
    final ClassMapper classMapper;

    public AddClassServiceImpl(TeacherMapper teacherMapper,
                               ClassMapper classMapper)
    {
        this.teacherMapper = teacherMapper;
        this.classMapper = classMapper;
    }



    /**
     * 管理员添加课程
     * @param classInfo 课程
     * @return map
     */
    @Override
    public Map<String, String> addClass(Class classInfo)
    {
        Map<String, String> map = new HashMap<>();
        int teacher_id = classInfo.getTeacher();

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", teacher_id);
        String teacher_name = teacherMapper.selectOne(queryWrapper).getName();

        classInfo.setTeacherName(teacher_name);
        classInfo.setCurrentNum(0);

        classMapper.insert(classInfo);

        map.put("status", "添加成功");

        System.out.println("debug: 添加课程 ");

        return map;
    }
}
