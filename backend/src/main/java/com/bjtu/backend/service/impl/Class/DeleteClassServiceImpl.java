package com.bjtu.backend.service.impl.Class;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.mapper.ClassStudentMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.ClassStudent;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Class.DeleteClassService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeleteClassServiceImpl implements DeleteClassService
{
    final ClassMapper classMapper;
    final ClassStudentMapper classStudentMapper;

    public DeleteClassServiceImpl(ClassMapper classMapper, ClassStudentMapper classStudentMapper)
    {
        this.classMapper = classMapper;
        this.classStudentMapper = classStudentMapper;
    }


    @Override
    public String delete(int id)
    {
        //删除 学生——课程 表中的数据
        QueryWrapper<ClassStudent> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("class_id", id);
        classStudentMapper.delete(queryWrapper1);

        //实际上还要删除作业，但目前没有

        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        if(!classMapper.exists(queryWrapper))
        {
            return "fail";
        }

        classMapper.delete(queryWrapper);

        System.out.println(TimeGenerateUtil.getTime() + " 删除课程 ");

        return "success";
    }
}
