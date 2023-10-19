package com.bjtu.backend.service.impl.User.Student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentResetPwdService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentResetPwdServiceImpl implements StudentResetPwdService
{

    final StudentMapper studentMapper;

    final PasswordEncoder passwordEncoder;

    final RedisTemplate redisTemplate;

    public StudentResetPwdServiceImpl(StudentMapper studentMapper, PasswordEncoder passwordEncoder, RedisTemplate redisTemplate)
    {
        this.studentMapper = studentMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 重设密码
     * @param student 学生类
     * @return map
     */
    @Override
    public Map<String, String> ResetPwd(Student student)
    {
        String password = passwordEncoder.encode(student.getPassword());
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", student.getNumber()).set("password", password);
        studentMapper.update(null, updateWrapper);

        Map<String, String> map = new HashMap<>();
        map.put("status", "重设密码");

        System.out.println("debug: 重设密码" + student.getNumber());

        return map;
    }
}
