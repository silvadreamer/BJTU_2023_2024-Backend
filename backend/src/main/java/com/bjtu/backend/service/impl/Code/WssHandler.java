package com.bjtu.backend.service.impl.Code;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.SubmissionMapper;
import com.bjtu.backend.pojo.Submission;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class WssHandler extends AbstractWebSocketHandler
{
    final private Submission submission;
    final SubmissionMapper submissionMapper;
    private Map<String, Object> map;


    public WssHandler(Submission submission,
                      SubmissionMapper submissionMapper)
    {
        this.submission = submission;
        this.submissionMapper = submissionMapper;
    }

    public Map<String , Object> getMap()
    {
        return map;
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
    {
        String json = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map1 = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        if(map1.containsKey("stderr"))
        {
            this.map = map1;
            String status = (String) map1.get("status");
            System.out.println(status);
            submission.setStatus(status);
            QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", submission.getId());
            submissionMapper.update(submission, queryWrapper);
        }
        System.out.println(json);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
    {
        System.out.println("connected to acwing");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
    {
        System.out.println("closed to acwing");
    }
}
