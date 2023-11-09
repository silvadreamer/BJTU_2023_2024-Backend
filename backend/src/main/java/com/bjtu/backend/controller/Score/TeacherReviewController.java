package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Score.TeacherReviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/score")
public class TeacherReviewController
{
    final TeacherReviewService teacherReviewService;

    public TeacherReviewController(TeacherReviewService teacherReviewService)
    {
        this.teacherReviewService =  teacherReviewService;
    }


    @PostMapping("/teacherReview")
    public Result<?> teacherReview(@RequestParam int score,
                                   @RequestParam int homeworkStudentId)
    {
        Map<String, Object> map = teacherReviewService.teacherReview(score, homeworkStudentId);

        return Result.success(map);
    }
}
