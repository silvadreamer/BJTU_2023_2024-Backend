package com.bjtu.backend.service.Homework;

import com.bjtu.backend.pojo.HomeworkStudent;

import java.util.Map;

public interface SubmitHomeworkService
{
    Map<String, Object> submit(HomeworkStudent homeworkStudent);

    void addFileName(int id, String fileName);
}
