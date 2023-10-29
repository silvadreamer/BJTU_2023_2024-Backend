package com.bjtu.backend.controller.OSS;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.OSS.OSSDownloadService;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oss")
public class OSSDownloadController
{
    final OSSDownloadService ossDownloadService;

    public OSSDownloadController(OSSDownloadService ossDownloadService)
    {
        this.ossDownloadService = ossDownloadService;
    }

    @PostMapping("/download")
    public Result<?> download(String fileName)
    {
        Map<String, Object> map = ossDownloadService.download(fileName);

        return Result.success(map);
    }

}
