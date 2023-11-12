package com.bjtu.backend.controller.File;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.File.TeacherFileService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class TeacherFileController
{
    final TeacherFileService teacherFileService;

    public TeacherFileController(TeacherFileService teacherFileService)
    {
        this.teacherFileService = teacherFileService;
    }


    @PostMapping("/teacher")
    public Result<?> addTeacher(@RequestParam MultipartFile multipartFile)
    {
        var map = teacherFileService.teacherFile(multipartFile);

        return Result.success(map);
    }
}
