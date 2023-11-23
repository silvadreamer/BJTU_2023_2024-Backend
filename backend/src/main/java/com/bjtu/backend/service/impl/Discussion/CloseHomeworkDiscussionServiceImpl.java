package com.bjtu.backend.service.impl.Discussion;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Discussion.CloseHomeworkDiscussionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloseHomeworkDiscussionServiceImpl implements CloseHomeworkDiscussionService
{
    final HomeworkMapper homeworkMapper;

    public CloseHomeworkDiscussionServiceImpl(HomeworkMapper homeworkMapper)
    {
        this.homeworkMapper = homeworkMapper;
    }

    @Override
    public Map<String, Object> close(int homeworkId)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<Homework>();
        queryWrapper.eq("id", homeworkId);
        Homework homework = homeworkMapper.selectOne(queryWrapper);
        homework.setDiscussion(0);
        homeworkMapper.update(homework, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("homework", homework);

        return map;
    }
}
