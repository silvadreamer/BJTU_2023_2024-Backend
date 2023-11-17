package com.bjtu.backend.controller.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.IO.Result;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Homework.ShowHomeworkService;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/homework")
public class ShowHomeworkController
{
    final HomeworkMapper homeworkMapper;
    final HomeworkStudentMapper homeworkStudentMapper;
    final ShowHomeworkService showHomeworkService;

    public ShowHomeworkController(HomeworkMapper homeworkMapper, ShowHomeworkService showHomeworkService,
                                  HomeworkStudentMapper homeworkStudentMapper)
    {
        this.homeworkMapper = homeworkMapper;
        this.showHomeworkService = showHomeworkService;
        this.homeworkStudentMapper = homeworkStudentMapper;
    }

    /**
     * 查看指定课程的所有布置作业
     * @param classID 课程id
     * @param pageNo 页码
     * @param pageSize 页数量
     * @return Result
     */
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

    @GetMapping("/showSubmitted")
    public Result<Map<String, Object>> showSubmittedList(@RequestParam(value = "classID") int classId,
                                                         @RequestParam(value = "homeworkID") int homeworkId,
                                                         @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                                                         @RequestParam(value = "pageSize", defaultValue = "15") Long pageSize)
    {

        Map<String, Object> map = showHomeworkService.showSubmitted(classId, homeworkId, pageNo, pageSize);

        return Result.success(map);
    }

    @GetMapping("/showForStudent")
    public Result<Map<String, Object>> showForStudent(@RequestParam(value = "classID") int classID,
                                                      @RequestParam(value = "studentID") int studentID,
                                                      @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = "15") Long pageSize)
    {
        Map<String, Object> map = showHomeworkService.showForStudent(classID, studentID, pageNo, pageSize);

        return Result.success(map);
    }
}
