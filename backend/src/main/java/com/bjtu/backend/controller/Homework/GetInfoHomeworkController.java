package com.bjtu.backend.controller.Homework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Homework.GetInfoHomeworkService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/homework")
public class GetInfoHomeworkController
{
    final GetInfoHomeworkService getInfoHomeworkService;

    public GetInfoHomeworkController(GetInfoHomeworkService getInfoHomeworkService)
    {
        this.getInfoHomeworkService = getInfoHomeworkService;
    }

    @GetMapping("/getInfo")
    public Result<?> getInfo(@RequestParam int id)
    {
        Map<String, Object> map = getInfoHomeworkService.getInfo(id);

        return Result.success(map);
    }
}
