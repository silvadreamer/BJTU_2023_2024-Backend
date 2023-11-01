package com.bjtu.backend.service.impl.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Homework.ShowHomeworkService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShowHomeworkServiceImpl implements ShowHomeworkService
{
    final HomeworkMapper homeworkMapper;
    final HomeworkStudentMapper homeworkStudentMapper;

    public ShowHomeworkServiceImpl(HomeworkMapper homeworkMapper, HomeworkStudentMapper homeworkStudentMapper)
    {
        this.homeworkMapper = homeworkMapper;
        this.homeworkStudentMapper = homeworkStudentMapper;
    }


    @Override
    public Map<String, Object> show(Page<Homework> page, QueryWrapper<Homework> queryWrapper)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("homeworkInfo", homeworkMapper.selectPage(page, queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " get homework list ");

        return map;
    }

    @Override
    public Map<String, Object> showSubmitted(Page<HomeworkStudent> page, QueryWrapper<HomeworkStudent> queryWrapper)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("homeworkInfo", homeworkStudentMapper.selectPage(page, queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " get submitted homework list ");

        return map;
    }
}
