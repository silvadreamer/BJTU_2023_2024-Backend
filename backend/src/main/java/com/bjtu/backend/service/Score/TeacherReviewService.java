package com.bjtu.backend.service.Score;

import java.util.Map;

public interface TeacherReviewService
{
    Map<String, Object> getReviewId(int homeworkId, String studentNumber);

    Map<String, Object> teacherReview(int score, int homeworkStudentId);
}
