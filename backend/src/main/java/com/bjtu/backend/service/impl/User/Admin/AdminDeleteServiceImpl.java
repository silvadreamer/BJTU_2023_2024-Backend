package com.bjtu.backend.service.impl.User.Admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.User.Admin.AdminDeleteService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminDeleteServiceImpl implements AdminDeleteService
{
    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;

    public AdminDeleteServiceImpl(StudentMapper studentMapper,
                                  TeacherMapper teacherMapper)
    {
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public Map<String, Object> deleteStudent(String number)
    {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);

        Map<String, Object> map = new HashMap<>();
        map.put("delete", studentMapper.selectOne(queryWrapper));

        studentMapper.delete(queryWrapper);

        System.out.println(TimeGenerateUtil.getTime() + " 删除学生账号 ");

        return map;
    }

    @Override
    public Map<String, Object> deleteTeacher(String number)
    {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);

        Map<String, Object> map = new HashMap<>();
        map.put("delete", teacherMapper.selectOne(queryWrapper));

        teacherMapper.delete(queryWrapper);

        System.out.println(TimeGenerateUtil.getTime() + " 删除教师账号 ");

        return map;
    }
}
