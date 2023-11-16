package com.bjtu.backend.service.impl.User.Student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.GetStudentListService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class GetStudentListServiceImpl implements GetStudentListService
{
    final StudentMapper studentMapper;

    public GetStudentListServiceImpl(StudentMapper studentMapper)
    {
        this.studentMapper = studentMapper;
    }

    @Override
    public Map<String, Object> getStudentList(String name, Long pageNo, Long pageSize)
    {

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "number");
        queryWrapper.like("name", name);
        Page<Student> page = new Page<>(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("page", studentMapper.selectPage(page, queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " 获得学生列表");

        return map;
    }
}
