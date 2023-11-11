package com.bjtu.backend.controller.ExcellentHomework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.ExcellentHomework.DeleteExcellentService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excellent")
public class DeleteExcellentController
{
    final DeleteExcellentService deleteExcellentService;

    public DeleteExcellentController(DeleteExcellentService deleteExcellentService)
    {
        this.deleteExcellentService = deleteExcellentService;
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam int homeworkStudentId)
    {
        var map = deleteExcellentService.delete(homeworkStudentId);

        return Result.success(map);
    }
}
