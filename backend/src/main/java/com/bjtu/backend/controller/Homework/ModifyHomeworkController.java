package com.bjtu.backend.controller.Homework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Homework.ModifyHomeworkService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homework")
public class ModifyHomeworkController
{
    final ModifyHomeworkService modifyHomeworkService;

    public ModifyHomeworkController(ModifyHomeworkService modifyHomeworkService)
    {
        this.modifyHomeworkService = modifyHomeworkService;
    }

    @PostMapping("/modify")
    public Result<?> modify(@RequestParam int id,
                            @RequestBody Homework homework)
    {
        modifyHomeworkService.modify(id, homework);

        return Result.success("success");
    }
}
