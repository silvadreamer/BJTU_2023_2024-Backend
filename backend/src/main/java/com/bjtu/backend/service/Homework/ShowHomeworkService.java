package com.bjtu.backend.service.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;

import java.util.Map;

public interface ShowHomeworkService
{
    /**
     * 查看
     * @param page page
     * @param queryWrapper queryWrapper
     * @return Map
     */
    Map<String, Object> show(Page<Homework> page, QueryWrapper<Homework> queryWrapper);


    Map<String, Object> showSubmitted(Page<HomeworkStudent> page, QueryWrapper<HomeworkStudent> queryWrapper);
}
