package com.bjtu.backend.service.Similarity;

import java.util.Map;

public interface JiebaSimilarityService
{
    Map<String, Object> jiebaSimilarity(int classId, int homeworkId);
}
