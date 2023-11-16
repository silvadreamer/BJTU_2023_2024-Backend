package com.bjtu.backend.service.User.Student;

import java.util.Map;

public interface GetStudentListService
{
    Map<String, Object> getStudentList(String name, Long pageNo, Long pageSize);
}
