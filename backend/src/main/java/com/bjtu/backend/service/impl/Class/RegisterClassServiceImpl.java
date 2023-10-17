package com.bjtu.backend.service.impl.Class;

import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.service.Class.RegisterClassService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Deprecated
@Service
public class RegisterClassServiceImpl implements RegisterClassService
{
    final ClassMapper classMapper;

    public RegisterClassServiceImpl(ClassMapper classMapper)
    {
        this.classMapper = classMapper;
    }


    /**
     * 教师注册课程表
     * 一般来说，注册的教师一定存在，暂不考虑教师号不存在的情况
     * @param classInfo 课程信息
     * @return map
     */
    @Override
    public Map<String, String> registerClass(Class classInfo)
    {
//        String info = classInfo.getInfo();
//        Integer id = classInfo.getId();
//        String name = classInfo.getName();
//
//        Map<String, String> map = new HashMap<>();

        classMapper.insert(classInfo);

        return null;
    }
}
