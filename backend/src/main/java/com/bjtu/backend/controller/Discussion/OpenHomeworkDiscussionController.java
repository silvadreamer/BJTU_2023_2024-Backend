package com.bjtu.backend.controller.Discussion;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Discussion.OpenHomeworkDiscussionService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discussion")
public class OpenHomeworkDiscussionController
{
    final OpenHomeworkDiscussionService openHomeworkDiscussionService;

    public OpenHomeworkDiscussionController(OpenHomeworkDiscussionService openHomeworkDiscussionService)
    {
        this.openHomeworkDiscussionService = openHomeworkDiscussionService;
    }

    @PostMapping("/open")
    public Result<?> open(@RequestParam int homeworkId)
    {
        var map = openHomeworkDiscussionService.openDiscussion(homeworkId);

        return Result.success(map);
    }
}
