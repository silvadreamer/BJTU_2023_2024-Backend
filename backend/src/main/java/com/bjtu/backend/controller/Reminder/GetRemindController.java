package com.bjtu.backend.controller.Reminder;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Reminder.GetRemindService;
import lombok.var;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reminder")
public class GetRemindController
{
    final GetRemindService getRemindService;

    public GetRemindController(GetRemindService getRemindService)
    {
        this.getRemindService = getRemindService;
    }

    @GetMapping("/info")
    public Result<?> getInfo(@RequestParam int id)
    {
        var map = getRemindService.getInfo(id);

        return Result.success(map);
    }
}
