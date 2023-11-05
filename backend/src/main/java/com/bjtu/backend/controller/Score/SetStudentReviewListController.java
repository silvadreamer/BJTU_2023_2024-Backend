package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Score.SetStudentReviewListService;
import com.bjtu.backend.service.impl.Score.SetStudentReviewListServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<?> SetReviewList(@RequestParam int homeworkId)
    {
        Map<String, String> map = setStudentReviewListService.setReviewList(homeworkId);

        return Result.success(map);
    }
}
