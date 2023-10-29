package com.bjtu.backend.service.impl.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Homework.AddHomeworkService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Service
public class AddHomeworkServiceImpl implements AddHomeworkService
{
    final HomeworkMapper homeworkMapper;

    public AddHomeworkServiceImpl(HomeworkMapper homeworkMapper)
    {
        this.homeworkMapper = homeworkMapper;
    }


    @Override
    public Map<String, Object> addHomework(Homework homework)
    {
        homeworkMapper.insert(homework);

        Map<String, Object> map = new HashMap<>();

        System.out.println("debug: add homework");
        map.put("status", homework);

        return map;
    }

    @Override
    public boolean checkHomework(int id)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        System.out.println("debug: check homework");

        return homeworkMapper.exists(queryWrapper);
    }

    @Override
    public Map<String, String> addFileName(int id, String FileName)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Homework homework = homeworkMapper.selectOne(queryWrapper);
        String fileNames = homework.getFileName();
        if(fileNames == null) fileNames = FileName;
        else fileNames = fileNames + "|" + FileName;
        homework.setFileName(fileNames);

        homeworkMapper.update(homework, queryWrapper);

        return null;
    }
}
