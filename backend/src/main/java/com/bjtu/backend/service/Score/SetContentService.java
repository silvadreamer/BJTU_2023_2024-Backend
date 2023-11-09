package com.bjtu.backend.service.Score;

import java.util.Map;

public interface SetContentService
{
    Map<String, Object> studentSetContent(String content, int id);

    Map<String, Object> teacherSetContent(String content, int id);
}
