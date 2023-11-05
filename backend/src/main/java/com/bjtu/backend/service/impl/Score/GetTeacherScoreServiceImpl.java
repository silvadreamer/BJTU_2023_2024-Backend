package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.TeacherScoreMapper;
import com.bjtu.backend.pojo.TeacherScore;
import com.bjtu.backend.service.Score.GetTeacherScoreService;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.HashMap;
import java.util.Map;

@Service
public class GetTeacherScoreServiceImpl implements GetTeacherScoreService
{
    final TeacherScoreMapper teacherScoreMapper;

    public GetTeacherScoreServiceImpl(TeacherScoreMapper teacherScoreMapper)
    {
        this.teacherScoreMapper = teacherScoreMapper;
    }

    @Override
    public Map<String, Integer> getScore(int homeworkStudentId)
    {
        QueryWrapper<TeacherScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_student_id", homeworkStudentId);

        Map<String, Integer> map = new HashMap<>();

        if(!teacherScoreMapper.exists(queryWrapper))
        {
            return map;
        }

        Integer score = teacherScoreMapper.selectOne(queryWrapper).getScore();

        if(score != null)
        {
            map.put("score", score);
        }

        return map;
    }
}
