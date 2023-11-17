package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Score.SetStudentHomeworkScoreService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/score")
public class SetStudentHomeworkScoreController
{
    final SetStudentHomeworkScoreService setStudentHomeworkScoreService;

    public SetStudentHomeworkScoreController(SetStudentHomeworkScoreService setStudentHomeworkScoreService)
    {
        this.setStudentHomeworkScoreService = setStudentHomeworkScoreService;
    }

    @PostMapping("/setStudentHomework")
    public Result<?> setStudentHomework(@RequestParam int homeworkStudentId)
    {
        Map<String, String> map = setStudentHomeworkScoreService.setScore(homeworkStudentId);

        return Result.success(map);
    }


    @PostMapping("/sabtxt")
    public Result<?> sabtxt(@RequestParam int homeworkId)
    {
        var map = setStudentHomeworkScoreService.SABTXT_simple(homeworkId);

        return Result.success(map);
    }
}
