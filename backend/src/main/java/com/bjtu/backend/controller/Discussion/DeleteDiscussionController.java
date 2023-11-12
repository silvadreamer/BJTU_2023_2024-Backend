package com.bjtu.backend.controller.Discussion;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Discussion.DeleteDiscussionService;
import lombok.var;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discussion")
public class DeleteDiscussionController
{
    final DeleteDiscussionService deleteDiscussionService;

    public DeleteDiscussionController(DeleteDiscussionService deleteDiscussionService)
    {
        this.deleteDiscussionService = deleteDiscussionService;
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam int id)
    {
        var map = deleteDiscussionService.delete(id);

        return Result.success(map);
    }
}
