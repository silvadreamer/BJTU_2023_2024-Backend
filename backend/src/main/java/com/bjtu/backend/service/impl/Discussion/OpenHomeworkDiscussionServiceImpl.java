package com.bjtu.backend.service.impl.Discussion;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Discussion.OpenHomeworkDiscussionService;
import org.springframework.stereotype.Service;

import javax.jms.QueueReceiver;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Service
public class OpenHomeworkDiscussionServiceImpl implements OpenHomeworkDiscussionService
{
    final HomeworkMapper homeworkMapper;

    public OpenHomeworkDiscussionServiceImpl(HomeworkMapper homeworkMapper)
    {
        this.homeworkMapper = homeworkMapper;
    }

    @Override
    public Map<String, Object> openDiscussion(int homeworkId)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", homeworkId);

        Homework homework = homeworkMapper.selectOne(queryWrapper);
        homework.setDiscussion(1);

        homeworkMapper.update(homework, queryWrapper);

        Map<String, Object> map = new HashMap<>();

        map.put("homework", homework);

        return map;
    }
}
