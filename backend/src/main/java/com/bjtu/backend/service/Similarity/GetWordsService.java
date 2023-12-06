package com.bjtu.backend.service.Similarity;

import java.util.Map;

public interface GetWordsService
{
    Map<String, Object> getContent(int classId, int homeworkId);

}
