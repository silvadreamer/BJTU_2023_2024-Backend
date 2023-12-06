package com.bjtu.backend.service.Similarity;

import java.util.Map;
import java.util.Objects;

public interface WordsSimilarityService
{
    Map<String, Object> checkSimilarity(int classId, int homeworkId);
}
