package com.bjtu.backend.service.impl.Code;

import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.SubmissionMapper;
import com.bjtu.backend.pojo.Submission;
import com.bjtu.backend.service.Code.CodeSubmitService;
import lombok.var;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.json.Json;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class CodeSubmitServiceImpl implements CodeSubmitService
{
    final SubmissionMapper submissionMapper;
    WssHandler wssHandler;

    public CodeSubmitServiceImpl(SubmissionMapper submissionMapper)
    {
        this.submissionMapper = submissionMapper;
    }

    @Override
    public Map<String, Object> submit(Submission submission)
    {
        Date now = new Date();
        submission.setDate(now);
        submissionMapper.insert(submission);

        wssHandler = new WssHandler(submission, submissionMapper);
        var map = wss(submission);

//        do
//        {
//            map = wssHandler.getMap();
//            //System.out.println(map.size());
//            if(map.containsKey("testcase_input"))
//            {
//                System.out.println(map.size());
//                break;
//            }
//        }
//        while(!map.containsKey("stderr"));

//        String status = (String) map.get("status");
//        System.out.println(status);
//        submission.setStatus(status);
//        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("id", submission.getId());
//        submissionMapper.update(submission, queryWrapper);

        return wssHandler.getMap();
    }

    public Map<String, Object> wss(Submission submission)
    {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.add("Cookie", "csrftoken=PwFc0Wrix116GL00TLAPwAzu2CU44ZD0SYheCz4IyAXUzFOipTEQFYgakOcr8Xwu; sessionid=d42zmwn1ac815at9rlc1rxp0qgx8kfq7");
        URI uri = URI.create("wss://www.acwing.com/wss/socket/");
        Map<String, Object> map = new HashMap<>();

        try
        {
            WebSocketSession session = webSocketClient.doHandshake(wssHandler, webSocketHttpHeaders, uri).get(10, TimeUnit.SECONDS);
            String code = submission.getContent();
            String id = Integer.toString(submission.getCodeHomeworkId());

            code = code.replace("\n", "\\n");
            System.out.println(code);
            System.out.println(id);

            String json = "{\n" +
                    "  \"activity\": \"problem_submit_code\",\n" +
                    "  \"problem_id\": " + id + ",\n" +
                    "  \"code\": \""+ code + "\",\n" +
                    "  \"language\": \"C++\",\n" +
                    "  \"mode\": \"normal\",\n" +
                    "  \"problem_activity_id\": 0,\n" +
                    "  \"record\": \"[]\",\n" +
                    "  \"program_time\": 0\n" +
                    "}";

            TextMessage textMessage = new TextMessage(json);

            session.sendMessage(textMessage);

//            do
//            {
//                map = wssHandler.getMap();
//                System.out.println(map.size());
//                if(map.containsKey("testcase_input")) break;
//            }
//            while(!map.containsKey("stderr"));

        }
        catch (InterruptedException | ExecutionException | TimeoutException | IOException e)
        {
            map.put("info", "评测失败");
            return map;
        }

        return map;
    }
}
