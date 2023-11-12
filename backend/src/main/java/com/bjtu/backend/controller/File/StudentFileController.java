package com.bjtu.backend.controller.File;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.File.StudentFileService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class StudentFileController
{

    final StudentFileService studentFileService;

    public StudentFileController(StudentFileService studentFileService)
    {
        this.studentFileService = studentFileService;
    }

    @PostMapping("/student")
    public Result<?> addStudentFile(@RequestParam MultipartFile multipartFile)
    {
        var map = studentFileService.studentFile(multipartFile);

        return Result.success(map);
    }
}
