package com.bjtu.backend.service.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.pojo.Homework;

import java.util.Map;

public interface ShowHomeworkService
{
    Map<String, Object> show(Page<Homework> page, QueryWrapper<Homework> queryWrapper);
}
