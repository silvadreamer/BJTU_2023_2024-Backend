package com.bjtu.backend.service.Class;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.pojo.Class;

import java.util.Map;

public interface ShowClassService
{
    Map<String, Object> getList(Page<Class> page, QueryWrapper<Class> queryWrapper);

    Map<String, Object> getInfo(int id);
}
