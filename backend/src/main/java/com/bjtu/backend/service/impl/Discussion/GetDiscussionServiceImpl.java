package com.bjtu.backend.service.impl.Discussion;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.DiscussionMapper;
import com.bjtu.backend.pojo.Discussion;
import com.bjtu.backend.service.Discussion.GetDiscussionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetDiscussionServiceImpl implements GetDiscussionService
{
    final DiscussionMapper discussionMapper;

    public GetDiscussionServiceImpl(DiscussionMapper discussionMapper)
    {
        this.discussionMapper = discussionMapper;
    }

    @Override
    public Map<String, Object> getDiscussion(int homeworkId, Long pageNo, Long pageSize)
    {
        Page<Discussion> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Discussion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);

        Map<String, Object> map = new HashMap<>();
        map.put("discussion", discussionMapper.selectPage(page, queryWrapper));

        return map;
    }
}
