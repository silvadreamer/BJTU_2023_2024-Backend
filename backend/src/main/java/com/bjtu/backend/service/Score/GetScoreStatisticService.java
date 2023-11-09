package com.bjtu.backend.service.Score;


import java.util.Map;

public interface GetScoreStatisticService
{
    Map<String, Object> getStatistics(int homeworkId);
}
