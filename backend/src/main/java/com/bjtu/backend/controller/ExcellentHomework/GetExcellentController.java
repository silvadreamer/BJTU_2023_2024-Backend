package com.bjtu.backend.controller.ExcellentHomework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.ExcellentHomework.GetExcellentService;
import com.sun.mail.imap.ResyncData;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/excellent")
public class GetExcellentController
{
    final GetExcellentService getExcellentService;

    public GetExcellentController(GetExcellentService getExcellentService)
    {
        this.getExcellentService = getExcellentService;
    }

    @GetMapping("/getPage")
    public Result<?> getPage(@RequestParam(value = "homeworkId") int homeworkId,
                             @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                             @RequestParam(value = "pageSize", defaultValue = "15") Long pageSize)
    {
        var map = getExcellentService.getList(homeworkId, pageNo, pageSize);

        return Result.success(map);
    }


    @GetMapping("/getInfo")
    public Result<?> getInfo(@RequestParam int homeworkStudentId)
    {
        var map = getExcellentService.getInfo(homeworkStudentId);

        return Result.success(map);
    }

    @GetMapping("/getDesc")
    public Result<?> getDesc(@RequestParam(value = "homeworkId") int homeworkId,
                             @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                             @RequestParam(value = "pageSize", defaultValue = "15") Long pageSize)
    {
        var map = getExcellentService.getDesc(homeworkId, pageNo, pageSize);

        return Result.success(map);
    }

}
