package com.bjtu.backend.service.Homework;

import java.util.Map;

public interface GetInfoHomeworkService
{
    Map<String, Object> getInfo(int id);

    Map<String, Object> getStudentHomeworkInfo(int id);
}
