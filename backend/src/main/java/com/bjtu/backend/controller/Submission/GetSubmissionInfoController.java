package com.bjtu.backend.controller.Submission;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Submission.GetSubmissionInfoService;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/submission")
public class GetSubmissionInfoController
{

    final GetSubmissionInfoService getSubmissionInfoService;

    public GetSubmissionInfoController(GetSubmissionInfoService getSubmissionInfoService)
    {
        this.getSubmissionInfoService = getSubmissionInfoService;
    }

    @GetMapping("/getInfo")
    public Result<?> getInfo(@RequestParam int id)
    {
        var map = getSubmissionInfoService.getInfo(id);

        return Result.success(map);
    }
}
