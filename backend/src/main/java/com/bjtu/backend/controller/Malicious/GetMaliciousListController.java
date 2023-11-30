package com.bjtu.backend.controller.Malicious;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Malicious.GetMaliciousListService;
import lombok.var;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/malicious")
public class GetMaliciousListController
{
    final GetMaliciousListService getMaliciousListService;

    public GetMaliciousListController(GetMaliciousListService getMaliciousListService)
    {
        this.getMaliciousListService = getMaliciousListService;
    }

    @GetMapping("/getList")
    public Result<?> getList(@RequestParam int homeworkId, @RequestParam(defaultValue = "1", required = false) Long pageNo,
                             @RequestParam(defaultValue = "10", required = false) Long pageSize)
    {
        var map = getMaliciousListService.getList(homeworkId, pageNo, pageSize);

        return Result.success(map);
    }

}
