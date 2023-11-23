package com.bjtu.backend.controller.Discussion;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Discussion.CloseHomeworkDiscussionService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discussion")
public class CloseHomeworkDiscussionController
{
    final CloseHomeworkDiscussionService closeHomeworkDiscussionService;

    public CloseHomeworkDiscussionController(CloseHomeworkDiscussionService closeHomeworkDiscussionService)
    {
        this.closeHomeworkDiscussionService = closeHomeworkDiscussionService;
    }

    @PostMapping("/close")
    public Result<?> close(@RequestParam int homeworkId)
    {
        var map = closeHomeworkDiscussionService.close(homeworkId);
        return Result.success(map);
    }
}
