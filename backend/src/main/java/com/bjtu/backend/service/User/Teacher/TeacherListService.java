package com.bjtu.backend.service.User.Teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.pojo.Users.Teacher;

import java.util.Map;

public interface TeacherListService
{
    Map<String, Object> list(QueryWrapper<Teacher> queryWrapper);
}
