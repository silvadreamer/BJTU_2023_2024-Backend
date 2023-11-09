package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.HomeworkReview;
import com.bjtu.backend.service.Score.SetStudentReviewListService;
import com.bjtu.backend.service.impl.Score.SetStudentReviewListServiceImpl;
import lombok.var;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/score")
public class SetStudentReviewListController
{
    final SetStudentReviewListService setStudentReviewListService;

    public SetStudentReviewListController(SetStudentReviewListService setStudentReviewListService)
    {
        this.setStudentReviewListService = setStudentReviewListService;
    }

    @PostMapping("/setReviewList")
    public Result<?> SetReviewList(@RequestBody HomeworkReview homeworkReview)
    {
        Date start = homeworkReview.getStart();
        Date end = homeworkReview.getEnd();
        int homeworkId = homeworkReview.getHomeworkId();
        double rate = homeworkReview.getStudentRate();

        var map = setStudentReviewListService.setReviewList(homeworkId, start, end, rate);

        return Result.success(map);
    }

    @PostMapping("/modifyReview")
    public Result<?> modify(@RequestBody HomeworkReview homeworkReview)
    {
        var map = setStudentReviewListService.modifyReview(homeworkReview);

        return Result.success(map);
    }
}
