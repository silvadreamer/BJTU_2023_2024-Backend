package com.bjtu.backend.service.Score;


import java.util.Map;

public interface GetTeacherScoreService
{
    Map<String, Object> getScore(int homeworkStudentId);
}
