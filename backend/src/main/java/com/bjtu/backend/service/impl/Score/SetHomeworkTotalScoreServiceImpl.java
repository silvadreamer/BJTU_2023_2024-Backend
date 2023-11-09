package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Score.SetHomeworkTotalScoreService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SetHomeworkTotalScoreServiceImpl implements SetHomeworkTotalScoreService
{
    final HomeworkMapper homeworkMapper;

    public SetHomeworkTotalScoreServiceImpl(HomeworkMapper homeworkMapper)
    {
        this.homeworkMapper = homeworkMapper;
    }

    @Override
    public Map<String, Object> setTotalScore(int homeworkId, int score)
    {
        UpdateWrapper<Homework> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", homeworkId).set("score", score);

        homeworkMapper.update(null, updateWrapper);

        Map<String, Object> map = new HashMap<>();

        map.put("分数", score);

        System.out.println(TimeGenerateUtil.getTime() + " 设置作业总分");

        return map;
    }
}
