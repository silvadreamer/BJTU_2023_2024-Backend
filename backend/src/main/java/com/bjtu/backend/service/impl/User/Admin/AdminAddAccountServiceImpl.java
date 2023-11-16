package com.bjtu.backend.service.impl.User.Admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Admin.AdminAddAccountService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminAddAccountServiceImpl implements AdminAddAccountService
{
    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;
    final PasswordEncoder passwordEncoder;

    public AdminAddAccountServiceImpl(StudentMapper studentMapper,
                                      TeacherMapper teacherMapper,
                                      PasswordEncoder passwordEncoder)
    {
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, Object> addStudent(String number, String name)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);

        if(studentMapper.exists(queryWrapper))
        {
            map.put("fail", "该学生已存在");
            return map;
        }

        Student student = new Student();
        student.setName(name);
        student.setNumber(number);
        student.setPassword(passwordEncoder.encode(number));
        studentMapper.insert(student);

        map.put("info", student);

        System.out.println(TimeGenerateUtil.getTime() + " 添加学生账号 ");

        return map;
    }

    @Override
    public Map<String, Object> addTeacher(String number, String name)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);

        if(teacherMapper.exists(queryWrapper))
        {
            map.put("fail", "该教师已存在");
            return map;
        }

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setNumber(number);
        teacher.setPassword(passwordEncoder.encode(number));
        teacherMapper.insert(teacher);

        System.out.println(TimeGenerateUtil.getTime() + " 添加教师账号 ");

        map.put("info", teacher);

        return map;
    }
}
