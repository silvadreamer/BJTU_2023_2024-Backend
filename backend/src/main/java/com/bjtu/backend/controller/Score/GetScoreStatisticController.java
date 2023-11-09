package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Score.GetScoreStatisticService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/score")
public class GetScoreStatisticController
{
    final GetScoreStatisticService getScoreStatisticService;

    public GetScoreStatisticController(GetScoreStatisticService getScoreStatisticService)
    {
        this.getScoreStatisticService = getScoreStatisticService;
    }

    @GetMapping("/statistic")
    public Result<?> getStatistic(@RequestParam int homeworkId)
    {
        Map<String, Object> map = getScoreStatisticService.getStatistics(homeworkId);

        return Result.success(map);
    }
}
