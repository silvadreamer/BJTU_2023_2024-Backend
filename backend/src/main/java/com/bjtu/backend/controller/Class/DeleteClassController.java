package com.bjtu.backend.controller.Class;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Class.DeleteClassService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class")
public class DeleteClassController
{
    final DeleteClassService deleteClassService;

    public DeleteClassController(DeleteClassService deleteClassService)
    {
        this.deleteClassService = deleteClassService;
    }


    @PostMapping("/delete")
    public Result<?> delete(@RequestParam int id)
    {
        if(deleteClassService.delete(id).equals("success"))
        {
            return Result.success("删除成功");
        }

        return Result.fail(20001, "删除失败");
    }

}
