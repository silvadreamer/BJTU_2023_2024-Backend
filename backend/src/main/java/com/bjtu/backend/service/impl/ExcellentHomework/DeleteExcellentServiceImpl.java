package com.bjtu.backend.service.impl.ExcellentHomework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.ExcellentHomeworkMapper;
import com.bjtu.backend.pojo.ExcellentHomework;
import com.bjtu.backend.service.ExcellentHomework.DeleteExcellentService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteExcellentServiceImpl implements DeleteExcellentService
{
    final ExcellentHomeworkMapper excellentHomeworkMapper;

    public DeleteExcellentServiceImpl(ExcellentHomeworkMapper excellentHomeworkMapper)
    {
        this.excellentHomeworkMapper = excellentHomeworkMapper;
    }

    @Override
    public Map<String, Object> delete(int homeworkStudentId)
    {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<ExcellentHomework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_student_id", homeworkStudentId);

        excellentHomeworkMapper.delete(queryWrapper);
        System.out.println(TimeGenerateUtil.getTime() + " 删除优秀作业");
        map.put("info", homeworkStudentId);

        return map;
    }
}
