package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Score.GetStudentScoreService;
import lombok.var;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
public class GetStudentScoreController
{
    final GetStudentScoreService getStudentScoreService;

    public GetStudentScoreController(GetStudentScoreService getStudentScoreService)
    {
        this.getStudentScoreService = getStudentScoreService;
    }


    @GetMapping("/getById")
    public Result<?> scoreById(@RequestParam int id)
    {
        var map = getStudentScoreService.getStudentScore(id);

        return Result.success(map);
    }

    @GetMapping("/getByHomework")
    public Result<?> scoreByHomework(@RequestParam int homeworkStudentId,
                                     @RequestParam String studentNumber)
    {
        var map = getStudentScoreService.getStudentScore(homeworkStudentId, studentNumber);

        return Result.success(map);
    }


}
