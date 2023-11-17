package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Score.GetTotalGradeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetTotalGradeServiceImpl implements GetTotalGradeService
{
    final HomeworkMapper homeworkMapper;
    final HomeworkStudentMapper homeworkStudentMapper;

    public GetTotalGradeServiceImpl(HomeworkMapper homeworkMapper,
                                    HomeworkStudentMapper homeworkStudentMapper)
    {
        this.homeworkMapper = homeworkMapper;
        this.homeworkStudentMapper = homeworkStudentMapper;
    }

    @Override
    public Map<String, Object> get(int homeworkStudentId)
    {
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", homeworkStudentId);
        int homeworkId = homeworkStudentMapper.selectOne(queryWrapper).getHomeworkId();
        QueryWrapper<Homework> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", homeworkId);

        Map<String, Object> map = new HashMap<>();
        map.put("score", homeworkMapper.selectOne(queryWrapper1).getScore());

        return map;
    }
}
