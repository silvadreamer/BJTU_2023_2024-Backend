package com.bjtu.backend.service.OSS;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface OSSUploadService
{
    Map<String, String> upload(MultipartFile multipartFile);

    Map<String, String> uploadHomework(String prefix, MultipartFile multipartFile);

    //Map<String, String> upload(MultipartFile multipartFile);
}
