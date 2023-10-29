package com.bjtu.backend.controller.Homework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Homework.DeleteHomeworkService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/homework")
public class DeleteHomeworkController
{
    final DeleteHomeworkService deleteHomeworkService;

    public DeleteHomeworkController(DeleteHomeworkService deleteHomeworkService)
    {
        this.deleteHomeworkService = deleteHomeworkService;
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam int id)
    {
        deleteHomeworkService.delete(id);

        return Result.success("success");
    }
}
