package com.bjtu.backend.service.Submission;


import java.util.Map;

public interface GetSubmissionListService
{
    Map<String, Object> getSubmissionListForStudent(String studentNumber, int codeInfoId, Long pageNo, Long pageSize);
}
