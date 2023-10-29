package com.bjtu.backend.controller.OSS;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.OSS.OSSDeleteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oss")
public class OSSDeleteController
{

    final OSSDeleteService ossDeleteService;

    public OSSDeleteController(OSSDeleteService ossDeleteService)
    {
        this.ossDeleteService = ossDeleteService;
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam String fileName)
    {
        boolean st = ossDeleteService.delete(fileName);

        if(st)
        {
            return Result.success("删除成功");
        }

        return Result.fail(20001, "删除失败");
    }

}
