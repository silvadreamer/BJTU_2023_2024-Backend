package com.bjtu.backend.service.impl.Discussion;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.DiscussionMapper;
import com.bjtu.backend.pojo.Discussion;
import com.bjtu.backend.service.Discussion.DeleteDiscussionService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteDiscussionServiceImpl implements DeleteDiscussionService
{
    final DiscussionMapper discussionMapper;

    public DeleteDiscussionServiceImpl(DiscussionMapper discussionMapper)
    {
        this.discussionMapper = discussionMapper;
    }

    @Override
    public Map<String, Object> delete(int id)
    {
        UpdateWrapper<Discussion> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("content", "**该内容已被删除**");
        updateWrapper.set("deleted", 1);
        updateWrapper.set("student_number", "-1");
        updateWrapper.set("teacher_number", "-1");

        discussionMapper.update(null, updateWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("discussion", discussionMapper.selectOne(updateWrapper));

        return map;
    }
}
