package com.bjtu.backend.service.impl.User.Teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Teacher.TeacherLoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TeacherLoginServiceImpl implements TeacherLoginService
{

    final TeacherMapper teacherMapper;

    final PasswordEncoder passwordEncoder;

    final RedisTemplate redisTemplate;

    public TeacherLoginServiceImpl(TeacherMapper teacherMapper, PasswordEncoder passwordEncoder, RedisTemplate redisTemplate)
    {
        this.teacherMapper = teacherMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Map<String, String> login(Teacher teacher)
    {
        String number = teacher.getNumber();
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        Teacher tea = teacherMapper.selectOne(queryWrapper);


        if(tea != null && passwordEncoder.matches(teacher.getPassword(), tea.getPassword()))
        {
            String key = "user" + UUID.randomUUID();
            tea.setPassword(null);
            redisTemplate.opsForValue().set(key, tea, 60, TimeUnit.MINUTES);


            Map<String, String> data = new HashMap<>();
            data.put("number", number);
            data.put("token", key);

            System.out.println("debug: login " + number);

            return data;
        }

        return null;

    }
}
