package com.bjtu.backend.service.impl.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Homework.GetInfoHomeworkService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


@Service
public class GetInfoHomeworkServiceImpl implements GetInfoHomeworkService
{
    final HomeworkMapper homeworkMapper;

    public GetInfoHomeworkServiceImpl(HomeworkMapper homeworkMapper)
    {
        this.homeworkMapper = homeworkMapper;
    }

    @Override
    public Map<String, Object> getInfo(int id)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Homework homework = homeworkMapper.selectOne(queryWrapper);

        String files = homework.getFileName();
        String[] parts = files.split("\\|");
        HashSet<String> uniqueParts = new HashSet<>();

        for (String part : parts)
        {
            uniqueParts.add(part.trim());
        }

        String[] uniquePartsArray = uniqueParts.toArray(new String[0]);

        map.put("info", homework);
        map.put("files", uniquePartsArray);

        System.out.println(TimeGenerateUtil.getTime() + " 获得作业信息");

        return map;
    }

}