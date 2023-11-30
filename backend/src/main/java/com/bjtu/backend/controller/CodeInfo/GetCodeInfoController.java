package com.bjtu.backend.controller.CodeInfo;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.CodeInfo.GetCodeInfoService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/codeInfo")
@RestController
public class GetCodeInfoController
{

    final GetCodeInfoService getCodeInfoService;

    public GetCodeInfoController(GetCodeInfoService getCodeInfoService)
    {
        this.getCodeInfoService = getCodeInfoService;
    }

    @PostMapping("/getInfo")
    public Result<?> getCodeInfo(@RequestParam int id)
    {
        var map = getCodeInfoService.getCodeInfo(id);

        return Result.success(map);
    }

}
