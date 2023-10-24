package com.bjtu.backend.service.impl.User.Teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Teacher.TeacherListService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherListServiceImpl implements TeacherListService
{
    final TeacherMapper teacherMapper;

    public TeacherListServiceImpl(TeacherMapper teacherMapper)
    {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public Map<String, Object> list(QueryWrapper<Teacher> queryWrapper)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("classInfo", teacherMapper.selectList(queryWrapper));

        System.out.println("debug: get teacher list ");

        return map;
    }
}
