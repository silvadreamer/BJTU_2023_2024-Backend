package com.bjtu.backend.service.impl.Similarity;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Similarity.SetCopyService;
import org.springframework.stereotype.Service;

import javax.sql.rowset.spi.SyncResolver;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Service
public class SetCopyServiceImpl implements SetCopyService
{
    final HomeworkStudentMapper homeworkStudentMapper;

    public SetCopyServiceImpl(HomeworkStudentMapper homeworkStudentMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
    }

    @Override
    public Map<String, Object> set(int homeworkId, int classId, String studentNumber)
    {
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("homework_id", homeworkId).eq("class_id", classId).eq("student_number", studentNumber);

        HomeworkStudent homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper);

        //将学生作业表的成绩设置为-1
        homeworkStudent.setScore(-1);

        homeworkStudentMapper.update(homeworkStudent, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("copy", homeworkStudent);

        return map;
    }
}
