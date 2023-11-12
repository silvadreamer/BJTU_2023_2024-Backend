package com.bjtu.backend.service.File;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ClassStudentFileService
{
    Map<String, Object> classStudent(MultipartFile multipartFile, int classId);
}
