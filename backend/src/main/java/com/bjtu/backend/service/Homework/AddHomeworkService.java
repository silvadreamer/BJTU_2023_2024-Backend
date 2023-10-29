package com.bjtu.backend.service.Homework;

import com.bjtu.backend.pojo.Homework;

import java.util.Map;

public interface AddHomeworkService
{
    Map<String, Object> addHomework(Homework homework);

    boolean checkHomework(int id);

    Map<String, String> addFileName(int id, String File);
}
