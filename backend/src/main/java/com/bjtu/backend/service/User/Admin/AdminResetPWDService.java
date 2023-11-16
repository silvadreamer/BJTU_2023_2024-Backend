package com.bjtu.backend.service.User.Admin;

import java.util.Map;

public interface AdminResetPWDService
{
    Map<String, Object> resetStudent(String number, String pwd);

    Map<String, Object> resetTeacher(String number, String pwd);
}
