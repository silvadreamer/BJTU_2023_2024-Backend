package com.bjtu.backend.service.Score;

import java.util.Map;

public interface SetStudentHomeworkScoreService
{
    /**
     * 对互评后的个人作业计算得分
     * @return Map
     */
    Map<String, String> setScore(int homeworkStudentId);
}
