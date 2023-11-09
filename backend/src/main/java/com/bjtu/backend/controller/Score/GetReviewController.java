package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Score.GetReviewService;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class GetReviewController
{
    final GetReviewService getReviewService;

    public GetReviewController(GetReviewService getReviewService)
    {
        this.getReviewService = getReviewService;
    }

    @GetMapping("/getReviewInfo")
    public Result<?> getReview(@RequestParam int homeworkId)
    {
        var map = getReviewService.getReview(homeworkId);

        return Result.success(map);
    }


}
