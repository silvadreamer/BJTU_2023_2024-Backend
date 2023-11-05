package com.bjtu.backend.service.Homework;

import java.util.Map;

public interface DeleteHomeworkService
{
    Map<String, String> delete(int id);

    void deleteFileName(int id, String File);

    void deleteFileNameForStudent(int id, String File);
}
