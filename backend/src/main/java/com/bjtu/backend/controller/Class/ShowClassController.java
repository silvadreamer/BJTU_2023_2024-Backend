package com.bjtu.backend.controller.Class;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.IO.Result;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.Class.ShowClassService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/class")
public class ShowClassController
{
    final ClassMapper classMapper;
    final TeacherMapper teacherMapper;
    final ShowClassService showClassService;

    public ShowClassController(ClassMapper classMapper, TeacherMapper teacherMapper, ShowClassService showClassService)
    {
        this.classMapper = classMapper;
        this.teacherMapper = teacherMapper;
        this.showClassService = showClassService;
    }

    /**
     * 展示课程列表
     * @param name 课程名（模糊搜索）（可选）
     * @param teacher 任课教师 （模糊搜索）（可选）
     * @param pageNo 页数 （默认1）
     * @param pageSize 每页个数 （默认 10）
     * @return Result
     */
    @GetMapping("/showList")
    public Result<Map<String, Object>> showList(@RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "teacher", required = false) String teacher,
                                               @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize)
    {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(name), "name", name);

        queryWrapper.like(StringUtils.hasLength(teacher), "teacher_name", teacher);
        queryWrapper.select("id", "name", "num", "teacher_name");

        Page<Class> page = new Page<>(pageNo, pageSize);
        Map<String, Object> map = showClassService.getList(page, queryWrapper);

        return Result.success(map);
    }


    /**
     * 获得指定编号课程的详细信息
     * @param id 编号
     * @return Result
     */
    @GetMapping("/showInfo")
    public Result<Map<String, Object>> showInfo(@RequestParam int id)
    {
        Map<String, Object> map = showClassService.getInfo(id);

        if(map.get("info") == null)
        {
            return Result.fail(20001, "课程不存在");
        }

        return Result.success(map);
    }

    @GetMapping("/teacherList")
    public Result<Map<String, Object>> teacherList(@RequestParam String number,
                                                   @RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize)
    {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher", number);
        queryWrapper.like(StringUtils.hasLength(name), "name", name);

        queryWrapper.select("id", "name", "num", "teacher_name", "current_num");

        Page<Class> page = new Page<>(pageNo, pageSize);
        Map<String, Object> map = showClassService.getList(page, queryWrapper);

        return Result.success(map);
    }

}
