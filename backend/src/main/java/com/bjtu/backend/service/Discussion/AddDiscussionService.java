package com.bjtu.backend.service.Discussion;

import java.util.Map;

public interface AddDiscussionService
{
    Map<String, Object> add(int homeworkId, String content, int reply,
                            String number, int index);
}
