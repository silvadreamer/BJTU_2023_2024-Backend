package com.bjtu.backend.service.Discussion;

import java.util.Map;

public interface GetDiscussionService
{
    Map<String, Object> getDiscussion(int homeworkId, Long pageNo, Long pageSize);
}
