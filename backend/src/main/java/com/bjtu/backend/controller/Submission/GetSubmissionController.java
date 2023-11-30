package com.bjtu.backend.controller.Submission;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Submission.GetSubmissionListService;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/submission")
@RestController
public class GetSubmissionController
{
    final GetSubmissionListService getSubmissionListService;

    public GetSubmissionController(GetSubmissionListService getSubmissionListService)
    {
        this.getSubmissionListService = getSubmissionListService;
    }

    @GetMapping("/getListForStudent")
    public Result<?> getList(@RequestParam String studentNumber,
                            @RequestParam int codeInfoId,
                            @RequestParam(defaultValue = "1", required = false) Long pageNo,
                            @RequestParam(defaultValue = "10", required = false) Long pageSize)
    {
        var map = getSubmissionListService.getSubmissionListForStudent(studentNumber, codeInfoId, pageNo, pageSize);

        return Result.success(map);
    }
}
