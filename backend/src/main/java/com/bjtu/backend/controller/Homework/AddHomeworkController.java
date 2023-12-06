package com.bjtu.backend.controller.Homework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Homework.AddHomeworkService;
import com.bjtu.backend.service.OSS.OSSUploadService;
import lombok.var;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/homework")
public class AddHomeworkController
{
    final AddHomeworkService addHomeworkService;
    final OSSUploadService ossUploadService;

    public AddHomeworkController(AddHomeworkService addHomeworkService, OSSUploadService ossUploadService)
    {
        this.addHomeworkService = addHomeworkService;
        this.ossUploadService = ossUploadService;
    }

    @PostMapping("/addCodeInfo")
    public Result<?> addCodeInfo(@RequestBody Homework homework)
    {
        var map = addHomeworkService.addCodeHomework(homework);

        return Result.success(map);
    }


    @PostMapping("/addContent")
    public Result<?> addContent(@RequestBody Homework homework)
    {
        Map<String, Object> map = addHomeworkService.addHomework(homework);

        if(!map.get("status").equals("success"))
        {
            return Result.fail(20001, "失败");
        }

        return Result.success(map);
    }

    /**
     *
     * @param id 作业编号
     * @param multipartFile 文件
     * @return Result
     */
    @PostMapping("/addFile")
    public Result<?> addFile(@RequestParam int id, @RequestParam int classID,  @RequestParam MultipartFile multipartFile)
    {
        //先检查作业是否存在
        if(!addHomeworkService.checkHomework(id))
        {
            return Result.fail(20001, "请先创建课程");
        }

        String prefix = classID + "/" + id;
        Map<String, String> map = ossUploadService.uploadHomework(prefix, multipartFile);

        if(map == null)
        {
            return Result.fail(20001, "上传失败！请稍后再试或联系管理员！");
        }

        String fileName = map.get("fileName");
        addHomeworkService.addFileName(id, fileName);

        return Result.success(map);
    }
}
