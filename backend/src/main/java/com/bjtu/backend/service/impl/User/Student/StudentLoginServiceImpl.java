package com.bjtu.backend.service.impl.User.Student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentLoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 学生登录业务实现
 */
@Service
public class StudentLoginServiceImpl implements StudentLoginService
{

    final StudentMapper studentMapper;

    final PasswordEncoder passwordEncoder;

    final RedisTemplate redisTemplate;

    public StudentLoginServiceImpl(StudentMapper studentMapper, PasswordEncoder passwordEncoder, RedisTemplate redisTemplate)
    {
        this.studentMapper = studentMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Map<String, String> login(Student stu)
    {
        String number = stu.getNumber();
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        Student student = studentMapper.selectOne(queryWrapper);


        if(student != null && passwordEncoder.matches(stu.getPassword(),student.getPassword()))
        {
            String key = "user" + UUID.randomUUID();
            //student.setPassword(null);
            redisTemplate.opsForValue().set(key, student, 60, TimeUnit.MINUTES);

            Map<String, String> data = new HashMap<>();
            data.put("number", number);
            data.put("token", key);

            System.out.println("debug: login " + number);

            return data;
        }

        return null;
    }
}
