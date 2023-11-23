package com.bjtu.backend.controller.Answers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.IO.Result;
import com.bjtu.backend.mapper.AnswersMapper;
import com.bjtu.backend.pojo.Answers;
import com.bjtu.backend.service.Answers.AddAnswerService;
import com.bjtu.backend.service.OSS.OSSUploadService;
import lombok.var;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/answer")
public class AddAnswerController
{
    final AddAnswerService addAnswerService;
    final AnswersMapper answersMapper;
    final OSSUploadService ossUploadService;

    public AddAnswerController(AddAnswerService addAnswerService,
                               AnswersMapper answersMapper,
                               OSSUploadService ossUploadService)
    {
        this.addAnswerService = addAnswerService;
        this.answersMapper = answersMapper;
        this.ossUploadService = ossUploadService;
    }


    @PostMapping("/addAnswer")
    public Result<?> addAnswer(@RequestBody Answers answers)
    {
        var map = addAnswerService.addAnswer(answers);

        return Result.success(map);
    }

    @PostMapping("/addFile")
    public Result<?> addFile(@RequestParam MultipartFile multipartFile,
                             @RequestParam int classId,
                             @RequestParam int homeworkId)
    {
        QueryWrapper<Answers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);
        if(!answersMapper.exists(queryWrapper))
        {
            return Result.fail(20001, "请先创建答案");
        }

        String prefix = classId + "/" + homeworkId + "/answer";
        var map = ossUploadService.uploadHomework(prefix, multipartFile);

        if(map == null)
        {
            return Result.fail(20001, "上传失败！请稍后再试或联系管理员！");
        }

        String fileName = map.get("fileName");
        addAnswerService.addFileName(answersMapper.selectOne(queryWrapper).getId(), fileName);

        return Result.success(map);
    }
}
