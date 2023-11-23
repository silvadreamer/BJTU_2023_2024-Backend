package com.bjtu.backend.controller.Answers;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.OSS.OSSDownloadService;
import lombok.var;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class DownloadAnswerController
{

    final OSSDownloadService ossDownloadService;

    public DownloadAnswerController(OSSDownloadService ossDownloadService)
    {
        this.ossDownloadService = ossDownloadService;
    }

    @GetMapping("/download")
    public Result<?> download(@RequestParam int homeworkId,
                              @RequestParam int classId,
                              @RequestParam String fileName)
    {
        String prefix = classId + "/" + homeworkId + "/answer";

        var map = ossDownloadService.downloadHomework(prefix, fileName);

        if(map == null)
        {
            return Result.fail(20001, "附件不存在，请重试");
        }

        return Result.success(map);
    }
}
