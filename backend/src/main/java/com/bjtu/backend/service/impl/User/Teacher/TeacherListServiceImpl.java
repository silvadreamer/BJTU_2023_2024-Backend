package com.bjtu.backend.service.impl.User.Teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Teacher.TeacherListService;
import com.bjtu.backend.utils.TimeGenerateUtil;
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
    public Map<String, Object> list(String name, Long pageNo, Long pageSize)
    {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("number", "name");
        queryWrapper.like("name", name);
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        Map<String, Object> map = new HashMap<>();

        map.put("list", teacherMapper.selectPage(page, queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " get teacher list ");

        return map;
    }
}
