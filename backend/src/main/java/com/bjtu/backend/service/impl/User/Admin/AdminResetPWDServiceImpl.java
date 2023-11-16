package com.bjtu.backend.service.impl.User.Admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Admin.AdminResetPWDService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminResetPWDServiceImpl implements AdminResetPWDService
{
    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;
    final PasswordEncoder passwordEncoder;

    public AdminResetPWDServiceImpl(StudentMapper studentMapper,
                                    TeacherMapper teacherMapper,
                                    PasswordEncoder passwordEncoder)
    {
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Map<String, Object> resetStudent(String number, String pwd)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);

        Student student = studentMapper.selectOne(queryWrapper);
        student.setPassword(passwordEncoder.encode(pwd));

        studentMapper.update(student, queryWrapper);

        map.put("info", student);

        System.out.println(TimeGenerateUtil.getTime() + " 修改学生密码 ");

        return map;
    }

    @Override
    public Map<String, Object> resetTeacher(String number, String pwd)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);

        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        teacher.setPassword(passwordEncoder.encode(pwd));

        teacherMapper.update(teacher, queryWrapper);

        map.put("info", teacher);

        System.out.println(TimeGenerateUtil.getTime() + " 修改教师密码 ");

        return map;
    }
}
