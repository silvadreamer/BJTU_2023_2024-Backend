package com.bjtu.backend.controller.Malicious;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Malicious.FindMaliciousScoreService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/malicious")
public class CheckMaliciousController
{
    final FindMaliciousScoreService findMaliciousScoreService;

    public CheckMaliciousController(FindMaliciousScoreService findMaliciousScoreService)
    {
        this.findMaliciousScoreService = findMaliciousScoreService;
    }

    @PostMapping("/zScore")
    public Result<?> z_score(@RequestParam int homeworkId, @RequestParam(required = false, defaultValue = "0.01") double bias)
    {
        var map = findMaliciousScoreService.z_score(homeworkId, bias);

        return Result.success(map);
    }
}
