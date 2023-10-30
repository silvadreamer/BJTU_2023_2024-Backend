package com.bjtu.backend.service.impl.User.Student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentRegisterService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 学生注册业务实现
 */
@Service
public class StudentRegisterServiceImpl implements StudentRegisterService
{
    final StudentMapper studentMapper;

    final PasswordEncoder passwordEncoder;

    final RedisTemplate redisTemplate;

    public StudentRegisterServiceImpl(StudentMapper studentMapper, PasswordEncoder passwordEncoder, RedisTemplate redisTemplate)
    {
        this.studentMapper = studentMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Map<String, String> register(Student stu)
    {
        String number = stu.getNumber();
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        Map<String, String> map = new HashMap<>();

        System.out.println(TimeGenerateUtil.getTime() + " 学生注册");

        if(studentMapper.selectOne(queryWrapper) == null)
        {
            stu.setPassword(passwordEncoder.encode(stu.getPassword()));
            studentMapper.insert(stu);
            map.put("status", "注册成功");
            return map;
        }

        return null;
    }
}
