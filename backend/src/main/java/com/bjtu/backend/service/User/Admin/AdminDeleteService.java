package com.bjtu.backend.service.User.Admin;

import java.util.Map;

public interface AdminDeleteService
{
    Map<String, Object> deleteStudent(String number);

    Map<String, Object> deleteTeacher(String number);
}
