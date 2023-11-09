package com.bjtu.backend.service.Score;

import java.util.Map;

public interface GetStudentScoreService
{
    /**
     * 根据学生批改表的id获得
     * @param id id
     * @return Map
     */
    Map<String, Object> getStudentScore(int id);

    /**
     * 根据批改人number和作业id获得
     * @param homeworkStudentId 批改人number
     * @param studentNumber 作业id
     * @return Map
     */
    Map<String, Object> getStudentScore(int homeworkStudentId, String studentNumber);
}
