package com.bjtu.backend.service.impl.ExcellentHomework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.ExcellentHomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.ExcellentHomework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.ExcellentHomework.GetExcellentService;
import com.bjtu.backend.service.Homework.GetInfoHomeworkService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetExcellentServiceImpl implements GetExcellentService
{
    final ExcellentHomeworkMapper excellentHomeworkMapper;

    final GetInfoHomeworkService getInfoHomeworkService;

    final HomeworkStudentMapper homeworkStudentMapper;

    public GetExcellentServiceImpl(ExcellentHomeworkMapper excellentHomeworkMapper,
                                   GetInfoHomeworkService getInfoHomeworkService,
                                   HomeworkStudentMapper homeworkStudentMapper)
    {
        this.excellentHomeworkMapper = excellentHomeworkMapper;
        this.getInfoHomeworkService = getInfoHomeworkService;
        this.homeworkStudentMapper = homeworkStudentMapper;
    }

    @Override
    public Map<String, Object> getInfo(int homeworkStudentId)
    {
        QueryWrapper<ExcellentHomework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_student_id", homeworkStudentId);

        ExcellentHomework excellentHomework = excellentHomeworkMapper.selectOne(queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("excellentInfo", excellentHomework);
        map.put("homeworkInfo", getInfoHomeworkService.getStudentHomeworkInfo(homeworkStudentId));

        System.out.println(TimeGenerateUtil.getTime() + " 获得优秀作业信息");

        return map;
    }

    @Override
    public Map<String, Object> getList(int homeworkId, Long pageNo, Long pageSize)
    {
        Map<String, Object> map = new HashMap<>();


        QueryWrapper<ExcellentHomework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);

        Page<ExcellentHomework> page = new Page<>(pageNo, pageSize);

        map.put("page", excellentHomeworkMapper.selectPage(page, queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " 获得优秀作业列表");

        return map;
    }

    @Override
    public Map<String, Object> getDesc(int homeworkId, Long pageNo, Long pageSize)
    {
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId).orderByDesc("score");

        Page<HomeworkStudent> page = new Page<>(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("page", homeworkStudentMapper.selectPage(page, queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " 从高到低排序");

        return map;
    }
}
