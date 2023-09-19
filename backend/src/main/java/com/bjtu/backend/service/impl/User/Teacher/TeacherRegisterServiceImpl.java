package com.bjtu.backend.service.impl.User.Teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Teacher.TeacherRegisterService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class TeacherRegisterServiceImpl implements TeacherRegisterService
{
    final TeacherMapper teacherMapper;

    final PasswordEncoder passwordEncoder;

    final RedisTemplate redisTemplate;

    public TeacherRegisterServiceImpl(TeacherMapper teacherMapper, PasswordEncoder passwordEncoder, RedisTemplate redisTemplate)
    {
        this.teacherMapper = teacherMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Map<String, String> register(Teacher teacher)
    {
        Map<String, String> map = new HashMap<>();
        String number = teacher.getNumber();
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);

        if(teacherMapper.selectOne(queryWrapper) == null)
        {
            teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
            teacherMapper.insert(teacher);
            map.put("status", "注册成功");
            return map;
        }

        return null;
    }
}
