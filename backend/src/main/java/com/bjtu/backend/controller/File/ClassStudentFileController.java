package com.bjtu.backend.controller.File;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.File.ClassStudentFileService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class ClassStudentFileController
{

    final ClassStudentFileService classStudentFileService;

    public ClassStudentFileController(ClassStudentFileService classStudentFileService)
    {
        this.classStudentFileService = classStudentFileService;
    }

    @PostMapping("/classStudent")
    public Result<?> addClassStudent(@RequestParam MultipartFile multipartFile,
                                     @RequestParam int classId)
    {
        var map = classStudentFileService.classStudent(multipartFile, classId);

        return Result.success(map);
    }
}
