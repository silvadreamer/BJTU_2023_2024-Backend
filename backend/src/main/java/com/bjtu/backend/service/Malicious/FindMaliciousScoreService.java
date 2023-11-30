package com.bjtu.backend.service.Malicious;

import java.util.Map;

public interface FindMaliciousScoreService
{
    Map<String, Object> z_score(int homeworkId, double bias);
}
