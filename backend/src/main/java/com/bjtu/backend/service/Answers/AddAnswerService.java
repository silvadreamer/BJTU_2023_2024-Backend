package com.bjtu.backend.service.Answers;

import com.bjtu.backend.pojo.Answers;

import java.util.Map;

public interface AddAnswerService
{
    Map<String, Object> addAnswer(Answers answers);

    Map<String, Object> addFileName(int answerId, String fileName);
}
