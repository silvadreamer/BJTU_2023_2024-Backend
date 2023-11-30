package com.bjtu.backend.controller.CodeInfo;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.CodeInfo.AddCodeInfoService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/codeInfo")
@RestController
public class AddCodeInfoController
{

    final AddCodeInfoService addCodeInfoService;

    public AddCodeInfoController(AddCodeInfoService addCodeInfoService)
    {
        this.addCodeInfoService = addCodeInfoService;
    }

    @PostMapping("/add")
    public Result<?> addCode(@RequestParam int id)
    {
        var map = addCodeInfoService.addCodeInfo(id);

        return Result.success(map);
    }
}
