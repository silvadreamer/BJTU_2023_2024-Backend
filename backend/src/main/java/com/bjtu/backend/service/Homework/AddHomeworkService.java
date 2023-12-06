package com.bjtu.backend.service.Homework;

import com.bjtu.backend.pojo.Homework;

import java.util.Map;

public interface AddHomeworkService
{
    Map<String, Object> addHomework(Homework homework);

    Map<String, Object> addCodeHomework(Homework homework);

    boolean checkHomework(int id);

    void addFileName(int id, String File);
}
