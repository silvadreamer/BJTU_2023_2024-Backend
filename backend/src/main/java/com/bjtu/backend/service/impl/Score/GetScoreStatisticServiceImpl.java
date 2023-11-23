package com.bjtu.backend.service.impl.Score;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Score.GetScoreStatisticService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetScoreStatisticServiceImpl implements GetScoreStatisticService
{
    final HomeworkStudentMapper homeworkStudentMapper;
    final HomeworkMapper homeworkMapper;
    final ClassMapper classMapper;

    public GetScoreStatisticServiceImpl(HomeworkStudentMapper homeworkStudentMapper,
                                        HomeworkMapper homeworkMapper,
                                        ClassMapper classMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.homeworkMapper = homeworkMapper;
        this.classMapper = classMapper;
    }

    @Override
    public Map<String, Object> getStatistics(int homeworkId)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);
        List<HomeworkStudent> list = homeworkStudentMapper.selectList(queryWrapper);

        QueryWrapper<Homework> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", homeworkId);
        int totalScore = homeworkMapper.selectOne(queryWrapper1).getScore();
        int classId = homeworkMapper.selectOne(queryWrapper1).getClassId();

        int score_90 = (int) (totalScore * 0.9);
        int score_80 = (int) (totalScore * 0.8);
        int score_70 = (int) (totalScore * 0.7);
        int score_60 = (int) (totalScore * 0.6);
        int num_90 = 0;
        int num_80 = 0;
        int num_70 = 0;
        int num_60 = 0;
        int num_lower = 0;

        for(HomeworkStudent homeworkStudent : list)
        {
            int score = homeworkStudent.getScore();

            if(score >= score_90 && score <= totalScore)
            {
                num_90 ++;
            }
            else if(score < score_90 && score >= score_80)
            {
                num_80 ++;
            }
            else if(score < score_80 && score >= score_70)
            {
                num_70 ++;
            }
            else if(score < score_70 && score >= score_60)
            {
                num_60 ++;
            }
            else
            {
                num_lower ++;
            }
        }

        map.put("0-60%", String.valueOf(num_lower));
        map.put("60%-70%", String.valueOf(num_60));
        map.put("70%-80%", String.valueOf(num_70));
        map.put("80%-90%", String.valueOf(num_80));
        map.put("90%-100%", String.valueOf(num_90));
        map.put("总数", list.size());

        QueryWrapper<Class> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id", classId);
        int studentTotalNum = classMapper.selectOne(queryWrapper2).getCurrentNum();
        map.put("应交人数", studentTotalNum);

        System.out.println(TimeGenerateUtil.getTime() + " 获得分数统计数据");

        return map;
    }
}
