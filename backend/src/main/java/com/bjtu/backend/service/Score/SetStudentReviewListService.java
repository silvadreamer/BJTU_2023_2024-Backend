package com.bjtu.backend.service.Score;

import com.bjtu.backend.pojo.HomeworkReview;

import java.util.Date;
import java.util.Map;

public interface SetStudentReviewListService
{
    Map<String, Object> setReviewList(int homeworkId, Date start, Date end, double rate);

    Map<String, Object> modifyReview(HomeworkReview homeworkReview);

}


