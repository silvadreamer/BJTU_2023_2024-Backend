package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Score.StudentReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/score")
public class StudentReviewController
{

    final StudentReviewService studentReviewService;

    public StudentReviewController(StudentReviewService studentReviewService)
    {
        this.studentReviewService = studentReviewService;
    }

    @GetMapping("/getReview")
    public Result<?> getReview(@RequestParam int homeworkId, @RequestParam String studentNumber)
    {
        Map<String, Object> map = studentReviewService.getReviewId(homeworkId, studentNumber);

        return Result.success(map);
    }

    @PostMapping("/studentReview")
    public Result<?> studentReview(@RequestParam int id, @RequestParam int score)
    {
        Map<String, String> map = studentReviewService.StudentReview(id, score);

        return Result.success(map);
    }

}
