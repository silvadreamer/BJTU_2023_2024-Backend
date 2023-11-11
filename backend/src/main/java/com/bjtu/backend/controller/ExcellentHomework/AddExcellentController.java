package com.bjtu.backend.controller.ExcellentHomework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.ExcellentHomework;
import com.bjtu.backend.service.ExcellentHomework.AddExcellentService;
import lombok.var;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/excellent")
public class AddExcellentController
{
    final AddExcellentService addExcellentService;

    public AddExcellentController(AddExcellentService addExcellentService)
    {
        this.addExcellentService = addExcellentService;
    }

    @PostMapping("/addBy1")
    public Result<?> add(@RequestParam int homeworkStudentId)
    {
        var map = addExcellentService.add(homeworkStudentId);

        return Result.success(map);
    }

    @PostMapping("/addBy2")
    public Result<?> add(@RequestBody ExcellentHomework excellentHomework)
    {
        String content = excellentHomework.getContent();
        int homeworkStudentId = excellentHomework.getHomeworkStudentId();
        var map = addExcellentService.add(homeworkStudentId, content);

        return Result.success(map);
    }

    @PostMapping("/addOrModify")
    public Result<?> addOrModify(@RequestBody ExcellentHomework excellentHomework)
    {
        String content = excellentHomework.getContent();
        int homeworkStudentId = excellentHomework.getHomeworkStudentId();

        var map = addExcellentService.addOrModifyContent(homeworkStudentId, content);

        return Result.success(map);
    }
}
