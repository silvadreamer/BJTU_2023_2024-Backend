package com.bjtu.backend.service.impl.ExcellentHomework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.ExcellentHomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.ExcellentHomework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.ExcellentHomework.AddExcellentService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddExcellentServiceImpl implements AddExcellentService
{
    final HomeworkStudentMapper homeworkStudentMapper;
    final ExcellentHomeworkMapper excellentHomeworkMapper;

    public AddExcellentServiceImpl(HomeworkStudentMapper homeworkStudentMapper,
                                   ExcellentHomeworkMapper excellentHomeworkMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.excellentHomeworkMapper = excellentHomeworkMapper;
    }

    @Override
    public Map<String, Object> add(int homeworkId, int homeworkStudentId)
    {
        return null;
    }

    @Override
    public Map<String, Object> add(int homeworkStudentId)
    {
        System.out.println(TimeGenerateUtil.getTime() + " 添加优秀作业");

        QueryWrapper<ExcellentHomework> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("homework_student_id", homeworkStudentId);
        if(excellentHomeworkMapper.exists(queryWrapper1))
        {
            Map<String, Object> map = new HashMap<>();
            map.put("info", "该作业已经是优秀作业！");
            return map;
        }

        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", homeworkStudentId);
        HomeworkStudent homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper);

        int homeworkId = homeworkStudent.getHomeworkId();

        ExcellentHomework excellentHomework = new ExcellentHomework();
        excellentHomework.setHomeworkId(homeworkId);
        excellentHomework.setHomeworkStudentId(homeworkStudentId);

        return addTo(homeworkStudentId, excellentHomework);
    }

    @Override
    public Map<String, Object> add(int homeworkStudentId, String content)
    {
        System.out.println(TimeGenerateUtil.getTime() + " 添加优秀作业");

        QueryWrapper<ExcellentHomework> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("homework_student_id", homeworkStudentId);
        if(excellentHomeworkMapper.exists(queryWrapper1))
        {
            Map<String, Object> map = new HashMap<>();
            map.put("info", "该作业已经是优秀作业！");
            return map;
        }

        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", homeworkStudentId);
        HomeworkStudent homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper);

        int homeworkId = homeworkStudent.getHomeworkId();

        ExcellentHomework excellentHomework = new ExcellentHomework();
        excellentHomework.setHomeworkId(homeworkId);
        excellentHomework.setHomeworkStudentId(homeworkStudentId);
        excellentHomework.setContent(content);

        return addTo(homeworkStudentId, excellentHomework);
    }

    @Override
    public Map<String, Object> addOrModifyContent(int homeworkStudentId, String content)
    {
        QueryWrapper<ExcellentHomework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", homeworkStudentId);
        ExcellentHomework excellentHomework = excellentHomeworkMapper.selectOne(queryWrapper);

        excellentHomework.setContent(content);
        excellentHomeworkMapper.update(excellentHomework, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("info", excellentHomework);

        System.out.println(TimeGenerateUtil.getTime() + " 优秀作业批语");

        return map;
    }

    private Map<String, Object> addTo(int homeworkStudentId, ExcellentHomework excellentHomework)
    {
        excellentHomeworkMapper.insert(excellentHomework);

        Map<String, Object> map = new HashMap<>();

        QueryWrapper<ExcellentHomework> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("homework_student_id", homeworkStudentId);
        excellentHomework = excellentHomeworkMapper.selectOne(queryWrapper1);

        map.put("info", excellentHomework);

        return map;
    }
}
