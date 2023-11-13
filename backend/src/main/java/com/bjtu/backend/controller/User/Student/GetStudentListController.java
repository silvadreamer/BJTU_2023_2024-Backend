package com.bjtu.backend.controller.User.Student;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.User.Student.GetStudentListService;
import lombok.var;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class GetStudentListController
{
    final GetStudentListService getStudentListService;

    public GetStudentListController(GetStudentListService getStudentListService)
    {
        this.getStudentListService = getStudentListService;
    }

    @GetMapping("/getList")
    public Result<?> getList(@RequestParam(defaultValue = "1") Long pageNo,
                             @RequestParam(defaultValue = "10") Long pageSize)
    {
        var map = getStudentListService.getStudentList(pageNo, pageSize);

        return Result.success(map);
    }

}
