package com.bjtu.backend.service.User.Admin;

import java.util.Map;

public interface AdminAddAccountService
{
    Map<String, Object> addStudent(String number, String name);

    Map<String, Object> addTeacher(String number, String name);
}
