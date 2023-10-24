package com.bjtu.backend.service.impl.Class;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.service.Class.ShowClassService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShowClassServiceImpl implements ShowClassService
{
    final ClassMapper classMapper;

    public ShowClassServiceImpl(ClassMapper classMapper)
    {
        this.classMapper = classMapper;
    }

    @Override
    public Map<String, Object> getList(Page<Class> page, QueryWrapper<Class> queryWrapper)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("classInfo", classMapper.selectPage(page, queryWrapper));

        System.out.println("debug: get class list ");

        return map;
    }

    @Override
    public Map<String, Object> getInfo(int id)
    {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Class class_info = classMapper.selectOne(queryWrapper);

        Map<String, Object> info = new HashMap<>();
        info.put("info", class_info);

        return info;
    }
}
