package com.bjtu.backend.controller.Score;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.StudentScore;
import com.bjtu.backend.pojo.TeacherScore;
import com.bjtu.backend.service.Score.SetContentService;
import org.springframework.web.bind.annotation.*;

import java.sql.PseudoColumnUsage;
import java.util.Map;

@RestController
@RequestMapping("/score")
public class SetContentController
{
    final SetContentService setContentService;

    public SetContentController(SetContentService setContentService)
    {
        this.setContentService = setContentService;
    }

    @PostMapping("/studentContent")
    public Result<?> studentContent(@RequestBody StudentScore studentScore)
    {
        String content = studentScore.getContent();
        int id = studentScore.getId();

        Map<String, Object> map = setContentService.studentSetContent(content, id);

        return Result.success(map);
    }


    @PostMapping("/teacherContent")
    public Result<?> teacherContent(@RequestBody TeacherScore teacherScore)
    {

        String content = teacherScore.getContent();
        int id = teacherScore.getId();

        System.out.println(id);

        Map<String, Object> map = setContentService.teacherSetContent(content, id);

        return Result.success(map);
    }


}
