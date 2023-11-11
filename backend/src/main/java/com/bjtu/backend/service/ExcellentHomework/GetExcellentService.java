package com.bjtu.backend.service.ExcellentHomework;

import java.util.Map;

public interface GetExcellentService
{
    Map<String, Object> getInfo(int homeworkStudentId);

    Map<String, Object> getList(int homeworkId, Long pageNo, Long pageSize);

    Map<String, Object> getDesc(int homeworkId, Long pageNo, Long pageSize);
}
