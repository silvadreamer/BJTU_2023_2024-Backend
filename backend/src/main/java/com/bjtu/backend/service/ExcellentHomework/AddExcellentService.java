package com.bjtu.backend.service.ExcellentHomework;


import java.util.Map;

public interface AddExcellentService
{
    Map<String, Object> add(int homeworkId, int homeworkStudentId);

    Map<String, Object> add(int homeworkStudentId);

    Map<String, Object> add(int homeworkStudentId, String content);

    Map<String, Object> addOrModifyContent(int homeworkStudentId, String content);

}
