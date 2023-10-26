package com.bjtu.backend.service.impl.Class;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.service.Class.ModifyClassService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModifyClassServiceImpl implements ModifyClassService
{
    final ClassMapper classMapper;

    public ModifyClassServiceImpl(ClassMapper classMapper)
    {
        this.classMapper = classMapper;
    }


    @Override
    public Map<String, String> modify(int id, String info)
    {

        UpdateWrapper<Class> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("info", info);
        classMapper.update(null, updateWrapper);

        Map<String, String> map = new HashMap<>();
        map.put("status", "更新成功");

        System.out.println("debug: 修改课程 ");

        return map;
    }
}
