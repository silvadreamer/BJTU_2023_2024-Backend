package com.bjtu.backend.controller.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.IO.Result;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Homework.ShowHomeworkService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/homework")
public class ShowHomeworkController
{
    final HomeworkMapper homeworkMapper;
    final ShowHomeworkService showHomeworkService;

    public ShowHomeworkController(HomeworkMapper homeworkMapper, ShowHomeworkService showHomeworkService)
    {
        this.homeworkMapper = homeworkMapper;
        this.showHomeworkService = showHomeworkService;
    }

    @GetMapping("/show")
    public Result<Map<String, Object>> showList(@RequestParam(value = "classID") int classID ,
                                                @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classID);

        queryWrapper.select("id", "class_id", "name", "end");

        Page<Homework> page = new Page<>(pageNo, pageSize);
        Map<String, Object> map = showHomeworkService.show(page, queryWrapper);

        return Result.success(map);
    }
}
