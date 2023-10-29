package com.bjtu.backend.service.Homework;

import com.bjtu.backend.pojo.HomeworkStudent;

import java.util.Map;

public interface SubmitHomeworkService
{
    Map<String, String> submit(HomeworkStudent homeworkStudent);
}
