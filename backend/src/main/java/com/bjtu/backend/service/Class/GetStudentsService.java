package com.bjtu.backend.service.Class;

import java.util.Map;

public interface GetStudentsService
{
    Map<String, Object> getStudents(int classId, Long pageNo, Long pageSize);
}
