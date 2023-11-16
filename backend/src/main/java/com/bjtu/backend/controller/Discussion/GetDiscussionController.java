package com.bjtu.backend.controller.Discussion;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Discussion.GetDiscussionService;
import lombok.var;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discussion")
public class GetDiscussionController
{
    final GetDiscussionService getDiscussionService;

    public GetDiscussionController(GetDiscussionService getDiscussionService)
    {
        this.getDiscussionService = getDiscussionService;
    }

    @GetMapping("/get")
    public Result<?> get(@RequestParam int homeworkId,
                         @RequestParam(defaultValue = "1") Long pageNo,
                         @RequestParam(defaultValue = "10") Long pageSize)
    {
        var map = getDiscussionService.getDiscussion(homeworkId, pageNo, pageSize);

        return Result.success(map);
    }
}
