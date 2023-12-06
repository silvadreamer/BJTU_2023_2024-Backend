package com.bjtu.backend.service.Similarity;


import java.util.Map;

public interface SetCopyService
{
    Map<String, Object> set(int homeworkId, int classId, String studentNumber);
}
