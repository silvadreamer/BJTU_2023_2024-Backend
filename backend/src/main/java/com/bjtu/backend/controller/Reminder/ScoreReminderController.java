package com.bjtu.backend.controller.Reminder;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Reminder.ScoreReminderService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminder")
public class ScoreReminderController
{
    final ScoreReminderService scoreReminderService;

    public ScoreReminderController(ScoreReminderService scoreReminderService)
    {
        this.scoreReminderService = scoreReminderService;
    }

    @PostMapping("/score")
    public Result<?> scoreRemind(@RequestParam int maliciousId)
    {
        var map = scoreReminderService.remindScore(maliciousId);

        return Result.success(map);
    }

}
