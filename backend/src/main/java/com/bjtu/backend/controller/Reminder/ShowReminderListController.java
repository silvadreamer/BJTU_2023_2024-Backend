package com.bjtu.backend.controller.Reminder;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Reminder.ShowReminderListService;
import lombok.var;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminder")
public class ShowReminderListController
{
    final ShowReminderListService showReminderListService;

    public ShowReminderListController(ShowReminderListService showReminderListService)
    {
        this.showReminderListService = showReminderListService;
    }

    @GetMapping("/list")
    public Result<?> showList(@RequestParam String studentNumber,
                              @RequestParam(defaultValue = "1") Long pageNo,
                              @RequestParam(defaultValue = "10") Long pageSize)
    {
        var map = showReminderListService.showReminder(studentNumber, pageNo, pageSize);

        return Result.success(map);
    }

    @GetMapping("/readList")
    public Result<?> showReadList(@RequestParam String studentNumber,
                                  @RequestParam(defaultValue = "1") Long pageNo,
                                  @RequestParam(defaultValue = "10") Long pageSize)
    {
        var map = showReminderListService.showRead(studentNumber, pageNo, pageSize);
        return Result.success(map);
    }

    @GetMapping("/unreadList")
    public Result<?> showUnReadList(@RequestParam String studentNumber,
                                    @RequestParam(defaultValue = "1") Long pageNo,
                                    @RequestParam(defaultValue = "10") Long pageSize)
    {
        var map = showReminderListService.showUnRead(studentNumber, pageNo, pageSize);

        return Result.success(map);
    }

}
