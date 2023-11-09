package com.bjtu.backend.service.Score;

import java.util.Map;

public interface SetHomeworkTotalScoreService
{
    Map<String, Object> setTotalScore(int homeworkId, int score);
}
