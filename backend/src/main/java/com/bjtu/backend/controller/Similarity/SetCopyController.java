package com.bjtu.backend.controller.Similarity;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Similarity.SetCopyService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/similarity")
public class SetCopyController
{
    final SetCopyService setCopyService;

    public SetCopyController(SetCopyService setCopyService)
    {
        this.setCopyService = setCopyService;
    }

    @PostMapping("/setCopy")
    public Result<?> setCopy(@RequestParam int homeworkId, @RequestParam int classId,
                             @RequestParam String studentNumber)
    {
        var map = setCopyService.set(homeworkId, classId, studentNumber);

        return Result.success(map);
    }
}
