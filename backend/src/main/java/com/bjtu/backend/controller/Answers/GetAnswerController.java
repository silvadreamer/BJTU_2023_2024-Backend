package com.bjtu.backend.controller.Answers;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Answers.GetAnswerService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
public class GetAnswerController
{
    final GetAnswerService getAnswerService;

    public GetAnswerController(GetAnswerService getAnswerService)
    {
        this.getAnswerService = getAnswerService;
    }

    @PostMapping("/get")
    public Result<?> get(@RequestParam int answerId)
    {
        var map = getAnswerService.get(answerId);

        return Result.success(map);
    }
}
