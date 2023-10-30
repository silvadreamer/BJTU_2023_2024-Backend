package com.bjtu.backend.controller.Homework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Homework.SubmitHomeworkService;
import com.bjtu.backend.service.OSS.OSSUploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/homework")
public class SubmitHomeworkController
{
    final OSSUploadService ossUploadService;

    final SubmitHomeworkService submitHomeworkService;

    public SubmitHomeworkController(OSSUploadService ossUploadService, SubmitHomeworkService submitHomeworkService)
    {
        this.ossUploadService = ossUploadService;
        this.submitHomeworkService = submitHomeworkService;
    }

    @PostMapping("/submit")
    public Result<?> submit(@RequestBody HomeworkStudent homeworkStudent)
    {
        Map<String, Object> map = submitHomeworkService.submit(homeworkStudent);

        return Result.success(map);
    }

    /**
     *
     * @param homeworkID 作业id
     * @param classID 课程id
     * @param studentID 学生id
     * @param id 学生作业id
     * @param multipartFile 文件
     * @return Result
     */
    @PostMapping("/submitFile")
    public Result<?> submitFile(@RequestParam int homeworkID,
                            @RequestParam int classID, @RequestParam String studentID,
                            @RequestParam int id, @RequestParam MultipartFile multipartFile)
    {
        String prefix = classID + "/" + homeworkID + "/" + studentID;
        Map<String, String> map = ossUploadService.uploadHomework(prefix, multipartFile);

        if(map == null)
        {
            return Result.fail(20001, "上传失败！请稍后再试或联系管理员！");
        }

        String fileName = map.get("fileName");
        submitHomeworkService.addFileName(id, fileName);

        return Result.success(map);
    }
}
