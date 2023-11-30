package com.bjtu.backend.service.Malicious;

import java.util.Map;

public interface GetMaliciousListService
{
    Map<String, Object> getList(int homeworkId, Long pageNo, Long pageSize);
}
