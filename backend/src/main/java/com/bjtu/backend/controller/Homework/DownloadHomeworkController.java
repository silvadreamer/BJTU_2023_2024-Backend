package com.bjtu.backend.controller.Homework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.OSS.OSSDownloadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/homework")
public class DownloadHomeworkController
{
    final OSSDownloadService ossDownloadService;

    public DownloadHomeworkController(OSSDownloadService ossDownloadService)
    {
        this.ossDownloadService = ossDownloadService;
    }

    @PostMapping("/download")
    public Result<?> download(int id, int classID, String fileName)
    {
        String prefix = classID + "/" + id;

        Map<String, Object> map = ossDownloadService.downloadHomework(prefix, fileName);

        if(map == null)
        {
            return Result.fail(20001,"附件不存在，请重新下载");
        }

        return Result.success(map);
    }
}
