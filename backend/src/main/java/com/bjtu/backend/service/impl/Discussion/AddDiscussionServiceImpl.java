package com.bjtu.backend.service.impl.Discussion;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.DiscussionMapper;
import com.bjtu.backend.pojo.Discussion;
import com.bjtu.backend.service.Discussion.AddDiscussionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddDiscussionServiceImpl implements AddDiscussionService
{
    final DiscussionMapper discussionMapper;

    public AddDiscussionServiceImpl(DiscussionMapper discussionMapper)
    {
        this.discussionMapper = discussionMapper;
    }

    /**
     * 讨论区回复
     * @param homeworkId 讨论区id
     * @param content 回复内容
     * @param reply 是否回复，0默认
     * @param number 账号
     * @param index 0表示学生，1表示教师
     * @return Map
     */
    @Override
    public Map<String, Object> add(int homeworkId, String content, int reply,
                                   String number, int index)
    {
        Discussion discussion = new Discussion();

        discussion.setContent(content);
        discussion.setHomeworkId(homeworkId);
        Date now = new Date();
        discussion.setTime(now);

        if(index == 1) discussion.setTeacherNumber(number);
        else discussion.setStudentNumber(number);

        if(reply != 0) discussion.setReply(reply);

        discussionMapper.insert(discussion);

        Map<String, Object> map = new HashMap<>();
        map.put("discussion", discussion);

        return map;
    }
}
