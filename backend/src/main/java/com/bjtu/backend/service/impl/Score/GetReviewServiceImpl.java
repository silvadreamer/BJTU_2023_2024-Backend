package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkReviewMapper;
import com.bjtu.backend.pojo.HomeworkReview;
import com.bjtu.backend.service.Score.GetReviewService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Service
public class GetReviewServiceImpl implements GetReviewService
{
    final HomeworkReviewMapper homeworkReviewMapper;

    public GetReviewServiceImpl(HomeworkReviewMapper homeworkReviewMapper)
    {
        this.homeworkReviewMapper = homeworkReviewMapper;
    }

    @Override
    public Map<String, Object> getReview(int homeworkId)
    {
        QueryWrapper<HomeworkReview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);

        Map<String, Object> map = new HashMap<>();

        map.put("review", homeworkReviewMapper.selectOne(queryWrapper));

        return map;
    }
}
