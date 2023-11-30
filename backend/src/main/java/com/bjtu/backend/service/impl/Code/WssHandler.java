package com.bjtu.backend.service.impl.Code;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.CodeMapper;
import com.bjtu.backend.mapper.SubmissionMapper;
import com.bjtu.backend.pojo.Code;
import com.bjtu.backend.pojo.Submission;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class WssHandler extends AbstractWebSocketHandler
{
    final private Submission submission;
    final SubmissionMapper submissionMapper;

    public WssHandler(Submission submission, SubmissionMapper submissionMapper, CodeMapper codeMapper)
    {
        this.submission = submission;
        this.submissionMapper = submissionMapper;
        this.codeMapper = codeMapper;
    }

    final CodeMapper codeMapper;


    private Map<String, Object> map;

    public Map<String , Object> getMap()
    {
        return map;
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
    {
        String json = message.getPayload();
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map1 = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        System.out.println(map1);
        if(map1.containsKey("stderr"))
        {
            this.map = map1;
            String status = (String) map1.get("status");
            submission.setStatus(status);

            if(status.equals("WRONG_ANSWER"))
            {
                String testcaseInput = (String) map1.get("testcase_input");
                String testcaseOutput = (String) map1.get("testcase_output");
                String testcaseUserOutput = (String) map1.get("testcase_user_output");

                submission.setTestcaseInput(testcaseInput);
                submission.setTestcaseOutput(testcaseOutput);
                submission.setUserOutput(testcaseUserOutput);
            }

            if(status.equals("ACCEPTED"))
            {
                QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("student_number", submission.getStudentNumber()).eq("code_info_id", submission.getCodeInfoId());
                Code code;
                if(codeMapper.exists(queryWrapper))
                {
                    code = codeMapper.selectOne(queryWrapper);
                    code.setContent(submission.getContent());
                    code.setDate(submission.getDate());
                    codeMapper.update(code, queryWrapper);
                }
                else
                {
                    code = new Code();
                    code.setStudentNumber(submission.getStudentNumber());
                    code.setCodeInfoId(submission.getCodeInfoId());
                    code.setContent(submission.getContent());
                    code.setDate(submission.getDate());
                    codeMapper.insert(code);
                }
            }

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
        System.out.println("Reason: " + status.getCode());
    }
}
