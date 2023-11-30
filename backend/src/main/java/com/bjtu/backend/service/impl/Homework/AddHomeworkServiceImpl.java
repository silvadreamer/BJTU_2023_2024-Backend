package com.bjtu.backend.service.impl.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Homework.AddHomeworkService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

        System.out.println(TimeGenerateUtil.getTime() + " add homework");

        map.put("status", "success");
        map.put("homework", homework);

        return map;
    }

    @Override
    public boolean checkHomework(int id)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        System.out.println(TimeGenerateUtil.getTime() + " check homework");

        return homeworkMapper.exists(queryWrapper);
    }

    @Override
    public void addFileName(int id, String FileName)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Homework homework = homeworkMapper.selectOne(queryWrapper);
        String fileNames = homework.getFileName();
        if(fileNames == null || fileNames.equals("")) fileNames = FileName;
        else fileNames = fileNames + "|" + FileName;
        homework.setFileName(fileNames);

        System.out.println(TimeGenerateUtil.getTime() + " 教师添加附件" + FileName);

        homeworkMapper.update(homework, queryWrapper);

    }
}
