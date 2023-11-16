package com.bjtu.backend.controller.Reminder;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Reminder.HomeworkRemindService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminder")
public class HomeworkReminderController
{
    final HomeworkRemindService homeworkRemindService;

    public HomeworkReminderController(HomeworkRemindService homeworkRemindService)
    {
        this.homeworkRemindService = homeworkRemindService;
    }

    @PostMapping("/homework")
    public Result<?> homeworkReminder(@RequestParam int homeworkId)
    {
        var map = homeworkRemindService.remindHomework(homeworkId);

        return Result.success(map);
    }

}
