package com.bjtu.backend.service.File;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TeacherFileService
{
    Map<String, Object> teacherFile(MultipartFile multipartFile);
}
