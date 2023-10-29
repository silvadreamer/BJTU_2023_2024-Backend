package com.bjtu.backend.service.impl.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Homework.DeleteHomeworkService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeleteHomeworkServiceImpl implements DeleteHomeworkService
{
    final HomeworkMapper homeworkMapper;

    public DeleteHomeworkServiceImpl(HomeworkMapper homeworkMapper)
    {
        this.homeworkMapper = homeworkMapper;
    }

    @Override
    public Map<String, String> delete(int id)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        homeworkMapper.delete(queryWrapper);

        return null;
    }
}
