package com.bjtu.backend.service.impl.User.Student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentChangePwdService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * 学生修改密码业务实现
 */
@Service
public class StudentChangePwdServiceImpl implements StudentChangePwdService
{
    final StudentMapper studentMapper;

    final PasswordEncoder passwordEncoder;

    final RedisTemplate redisTemplate;

    public StudentChangePwdServiceImpl(StudentMapper studentMapper, PasswordEncoder passwordEncoder, RedisTemplate redisTemplate)
    {
        this.studentMapper = studentMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Map<String, String> changePWD(Student student)
    {
        System.out.println(student.getPassword());
        String password = passwordEncoder.encode(student.getPassword());
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", student.getNumber()).set("password", password);
        studentMapper.update(null, updateWrapper);

        Map<String, String> map = new HashMap<>();
        map.put("status", "修改密码");

        System.out.println("debug: 修改密码" + student.getNumber());

        return map;
    }
}
