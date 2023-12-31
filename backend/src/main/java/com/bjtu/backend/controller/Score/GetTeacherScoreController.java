package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Score.GetTeacherScoreService;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/score")
public class GetTeacherScoreController
{
    final GetTeacherScoreService getTeacherScoreService;

    public GetTeacherScoreController(GetTeacherScoreService getTeacherScoreService)
    {
        this.getTeacherScoreService = getTeacherScoreService;
    }

    @GetMapping("/getTeacherScore")
    public Result<?> getScore(@RequestParam int homeworkStudentId)
    {
        var map = getTeacherScoreService.getScore(homeworkStudentId);

        if(map.isEmpty())
        {
            return Result.fail(20001, "教师尚未评阅");
        }

//        if(map.get("score") != -1)
//        {
            return Result.success(map);
//        }

        //return Result.fail(20001, "教师尚未评阅");
    }
}
