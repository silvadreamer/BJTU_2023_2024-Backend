package com.bjtu.backend.controller.Code;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Code.CodeCheckService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class CodeCheckController
{
    final CodeCheckService codeCheckService;

    public CodeCheckController(CodeCheckService codeCheckService)
    {
        this.codeCheckService = codeCheckService;
    }

    @PostMapping("/check")
    public Result<?> check(@RequestParam int id)
    {
        var map = codeCheckService.check(id);

        return Result.success(map);
    }

}
