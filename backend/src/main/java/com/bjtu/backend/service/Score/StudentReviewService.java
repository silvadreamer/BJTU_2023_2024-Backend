package com.bjtu.backend.service.Score;


import java.util.Map;

public interface StudentReviewService
{
    Map<String, Object> getReviewId(int homeworkId, String studentNumber);

    Map<String, String> StudentReview(int id, int score);

}
