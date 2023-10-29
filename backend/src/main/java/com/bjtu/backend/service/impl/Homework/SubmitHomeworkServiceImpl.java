package com.bjtu.backend.service.impl.Homework;


import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Homework.SubmitHomeworkService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SubmitHomeworkServiceImpl implements SubmitHomeworkService
{
    final HomeworkStudentMapper homeworkStudentMapper;

    public SubmitHomeworkServiceImpl(HomeworkStudentMapper homeworkStudentMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
    }

    @Override
    public Map<String, String> submit(HomeworkStudent homeworkStudent)
    {
        return null;
    }
}
