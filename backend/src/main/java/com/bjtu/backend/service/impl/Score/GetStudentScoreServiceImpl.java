package com.bjtu.backend.service.impl.Score;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.StudentScoreMapper;
import com.bjtu.backend.pojo.StudentScore;
import com.bjtu.backend.service.Score.GetStudentScoreService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetStudentScoreServiceImpl implements GetStudentScoreService
{
    final StudentScoreMapper studentScoreMapper;

    public GetStudentScoreServiceImpl(StudentScoreMapper studentScoreMapper)
    {
        this.studentScoreMapper = studentScoreMapper;
    }

    @Override
    public Map<String, Object> getStudentScore(int id)
    {
        QueryWrapper<StudentScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Map<String, Object> map = new HashMap<>();
        map.put("score", studentScoreMapper.selectOne(queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " 获得学生互评内容");

        return map;
    }

    @Override
    public Map<String, Object> getStudentScore(int homeworkStudentId, String studentNumber)
    {
        QueryWrapper<StudentScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number", studentNumber).eq("homework_student_id", homeworkStudentId);

        Map<String, Object> map = new HashMap<>();
        map.put("score", studentScoreMapper.selectOne(queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " 获得学生互评内容");

        return map;
    }
}
