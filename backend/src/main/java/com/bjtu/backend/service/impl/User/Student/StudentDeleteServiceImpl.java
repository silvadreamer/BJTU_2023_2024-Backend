package com.bjtu.backend.service.impl.User.Student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentDeleteService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 学生注销账户业务实现
 */
@Service
public class StudentDeleteServiceImpl implements StudentDeleteService
{
    StudentMapper studentMapper;

    public StudentDeleteServiceImpl(StudentMapper studentMapper)
    {
        this.studentMapper = studentMapper;
    }

    @Override
    public Map<String, String> delete(Student student)
    {
        String number = student.getNumber();
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        studentMapper.delete(queryWrapper);

        Map<String, String> map = new HashMap<>();
        map.put("status", "删除成功");

        System.out.println(TimeGenerateUtil.getTime() + " 注销" + number);

        return map;
    }
}
