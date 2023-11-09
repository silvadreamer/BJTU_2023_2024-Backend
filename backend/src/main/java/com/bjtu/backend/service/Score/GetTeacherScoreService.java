package com.bjtu.backend.service.Score;


import java.util.Map;

public interface GetTeacherScoreService
{
    Map<String, Integer> getScore(int homeworkStudentId);
}
