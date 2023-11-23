package com.bjtu.backend.service.impl.Discussion;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.DiscussionMapper;
import com.bjtu.backend.pojo.Discussion;
import com.bjtu.backend.service.Discussion.GetDiscussionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

        List<Discussion> list = discussionMapper.selectList(queryWrapper);

        for(Discussion discussion : list)
        {
            int reply = discussion.getReply();
            if(reply != 0)
            {
                QueryWrapper<Discussion> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", reply);

                Discussion discussion1 = discussionMapper.selectOne(queryWrapper1);

                String studentNumber = discussion1.getStudentNumber();
                String teacherNumber = discussion1.getTeacherNumber();
                String content = discussion1.getContent();
                if(content.length() > 15)
                {
                    content = content.substring(0, 15);
                    content += "...";
                }

                discussion.setReplyContent(content);

                if(!studentNumber.equals("-1"))
                {
                    discussion.setReplyStudentNumber(studentNumber);
                }
                else if(!teacherNumber.equals("-1"))
                {
                    discussion.setReplyTeacherNumber(teacherNumber);
                }
                else
                {
                    discussion.setReplyTeacherNumber("-1");
                    discussion.setReplyStudentNumber("-1");
                }

                queryWrapper1.clear();
                queryWrapper1.eq("id", discussion.getId());
                discussionMapper.update(discussion, queryWrapper1);
            }
        }

        map.put("discussion", discussionMapper.selectPage(page, queryWrapper));

        return map;
    }
}
