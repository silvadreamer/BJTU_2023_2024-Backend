package com.bjtu.backend.controller.OSS;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.OSS.OSSUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/oss")
@RestController
public class OSSUploadController
{
    final OSSUploadService ossUploadService;

    public OSSUploadController(OSSUploadService ossUploadService)
    {
        this.ossUploadService = ossUploadService;
    }

    @PostMapping("/upload")
    public Result<?> upload(@RequestParam MultipartFile multipartFile)
    {
        Map<String, String> map = ossUploadService.upload(multipartFile);

        if(map == null)
        {
            return Result.fail(20001, "上传失败！请稍后再试或联系管理员！");
        }

        return Result.success(map);
    }
}
