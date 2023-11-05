package com.bjtu.backend.service.Score;


import java.util.Map;

public interface GetTeacherScoreService
{
    public Map<String, Integer> getScore(int homeworkStudentId);
}
