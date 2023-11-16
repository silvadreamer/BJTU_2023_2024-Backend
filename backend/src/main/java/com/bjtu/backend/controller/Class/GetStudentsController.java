package com.bjtu.backend.controller.Class;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Class.GetStudentsService;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class")
public class GetStudentsController
{
    final GetStudentsService getStudentsService;

    public GetStudentsController(GetStudentsService getStudentsService)
    {
        this.getStudentsService = getStudentsService;
    }

    @GetMapping("/student")
    public Result<?> getStudents(@RequestParam int classId, @RequestParam(defaultValue = "1") Long pageNo,
                                 @RequestParam(defaultValue = "10") Long pageSize)
    {
        var map = getStudentsService.getStudents(classId, pageNo, pageSize);

        return Result.success(map);
    }
}
