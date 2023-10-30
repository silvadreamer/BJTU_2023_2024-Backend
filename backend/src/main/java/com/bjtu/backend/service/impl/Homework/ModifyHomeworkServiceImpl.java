package com.bjtu.backend.service.impl.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Homework.ModifyHomeworkService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ModifyHomeworkServiceImpl implements ModifyHomeworkService
{
    final HomeworkMapper homeworkMapper;

    public ModifyHomeworkServiceImpl(HomeworkMapper homeworkMapper)
    {
        this.homeworkMapper = homeworkMapper;
    }


    @Override
    public Map<String, String> modify(int id, Homework homework)
    {
        UpdateWrapper<Homework> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);

        homeworkMapper.update(homework, updateWrapper);

        System.out.println(TimeGenerateUtil.getTime() + " 修改作业内容");

        return null;
    }
}
