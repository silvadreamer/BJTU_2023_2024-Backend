package com.bjtu.backend.service.File;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface StudentFileService
{
    Map<String, Object> studentFile(MultipartFile multipartFile);
}
