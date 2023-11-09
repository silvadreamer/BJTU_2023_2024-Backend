package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Score.SetHomeworkTotalScoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/score")
public class SetHomeworkTotalScoreController
{
    final SetHomeworkTotalScoreService setHomeworkTotalScoreService;

    public SetHomeworkTotalScoreController(SetHomeworkTotalScoreService setHomeworkTotalScoreService)
    {
        this.setHomeworkTotalScoreService = setHomeworkTotalScoreService;
    }

    @PostMapping("/setTotal")
    public Result<?> setTotal(@RequestParam int homeworkId, @RequestParam int score)
    {
        Map<String, Object> map = setHomeworkTotalScoreService.setTotalScore(homeworkId, score);
        return Result.success(map);
    }
}
