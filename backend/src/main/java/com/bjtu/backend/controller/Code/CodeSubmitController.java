package com.bjtu.backend.controller.Code;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.mapper.SubmissionMapper;
import com.bjtu.backend.pojo.Submission;
import com.bjtu.backend.service.Code.CodeSubmitService;
import lombok.var;
import org.apache.ibatis.session.ResultContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/code")
public class CodeSubmitController
{
    final CodeSubmitService codeSubmitService;

    public CodeSubmitController(CodeSubmitService codeSubmitService)
    {
        this.codeSubmitService = codeSubmitService;
    }

    @PostMapping("/submit")
    public Result<?> submit(@RequestBody Submission submission) throws ExecutionException, InterruptedException, TimeoutException
    {
        var map = codeSubmitService.submit(submission);
        return Result.success(map);
    }

}
