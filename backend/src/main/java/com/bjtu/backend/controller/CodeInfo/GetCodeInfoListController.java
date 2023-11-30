package com.bjtu.backend.controller.CodeInfo;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.CodeInfo.GetCodeInfoListService;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.annotation.RequestMap;
import java.util.Map;

@RestController
@RequestMapping("/codeInfo")
public class GetCodeInfoListController
{

    final GetCodeInfoListService getCodeInfoListService;

    public GetCodeInfoListController(GetCodeInfoListService getCodeInfoListService)
    {
        this.getCodeInfoListService = getCodeInfoListService;
    }

    @GetMapping("/listForStudent")
    public Result<?> getListForStudent(@RequestParam(defaultValue = "1", required = false) Long pageNo,
                                    @RequestParam(defaultValue = "10", required = false) Long pageSize)
    {
        var map = getCodeInfoListService.getListStudent(pageNo, pageSize);

        return Result.success(map);
    }
}
