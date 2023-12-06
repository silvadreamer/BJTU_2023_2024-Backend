package com.bjtu.backend.service.impl.Code;

import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.CodeMapper;
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
    final CodeMapper codeMapper;

    String header = "file_3132784_readed=\"\"; file_8687507_readed=\"\"; csrftoken=GiqRia4TB2SoHvRXuGuHjLuOhCQ4xTiqyJdGo2Fs6Jsope7M3iWBjaVH8rwaPTSi; sessionid=m05qrw1ybp4xogqd8svh1c90jc366d40; file_1061_readed=\"\"; file_4476078_readed=\"\"";

    public CodeSubmitServiceImpl(SubmissionMapper submissionMapper, CodeMapper codeMapper)
    {
        this.submissionMapper = submissionMapper;
        this.codeMapper = codeMapper;
    }

    WssHandler wssHandler;

    @Override
    public String updateHeader(String s)
    {
        this.header = s;

        return header;
    }


    @Override
    public Map<String, Object> submit(Submission submission)
    {
        Date now = new Date();
        submission.setDate(now);
        submissionMapper.insert(submission);

        wssHandler = new WssHandler(submission, submissionMapper, codeMapper);
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
        webSocketHttpHeaders.add("Cookie", header);
        URI uri = URI.create("wss://www.acwing.com/wss/socket/");
        Map<String, Object> map = new HashMap<>();

        try
        {
            WebSocketSession session = webSocketClient.doHandshake(wssHandler, webSocketHttpHeaders, uri).get(10, TimeUnit.SECONDS);
            session.setTextMessageSizeLimit(1024 * 1024);
            String code = submission.getContent();
            String id = Integer.toString(submission.getCodeInfoId());
            System.out.println(code);


            code = code.replace("\\", "\\\\").replace("\"", "\\\"");
            code = code.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
            //code = code.replace("\t", "\\t");
            //code = code.replaceAll("([^\r])\n", "$1\\n");

            // 将双引号转义
            //code = code.replace("\"", "\\\"");

//            code = code.replace("\"", "\\\"");
//
            //code = code.replace("\n", "\\n");
            //code = code.replace("\\\n", "\\\\n");

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

            System.out.println(json);

            TextMessage textMessage = new TextMessage(json);

            session.sendMessage(textMessage);

        }
        catch (InterruptedException | ExecutionException | TimeoutException | IOException e)
        {
            map.put("info", "评测失败");
            return map;
        }

        return map;
    }
}
