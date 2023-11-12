package com.bjtu.backend.controller.Discussion;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Discussion;
import com.bjtu.backend.service.Discussion.AddDiscussionService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discussion")
public class AddDiscussionController
{
    final AddDiscussionService addDiscussionService;

    public AddDiscussionController(AddDiscussionService addDiscussionService)
    {
        this.addDiscussionService = addDiscussionService;
    }

    @PostMapping("/add")
    public Result<?> addDiscussion(@RequestBody Discussion discussion)
    {
        String number = "";
        int index = 0;
        if (discussion.getStudentNumber() != null) number = discussion.getStudentNumber();
        else
        {
            number = discussion.getTeacherNumber();
            index = 1;
        }

        int homeworkId = discussion.getHomeworkId();
        int reply = discussion.getReply();
        String content = discussion.getContent();

        var map = addDiscussionService.add(homeworkId, content, reply, number, index);

        return Result.success(map);
    }
}
