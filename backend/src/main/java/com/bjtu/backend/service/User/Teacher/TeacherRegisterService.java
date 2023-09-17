package com.bjtu.backend.service.User.Teacher;

import com.bjtu.backend.pojo.Users.Teacher;

import java.util.Map;

public interface TeacherRegisterService
{
    Map<String, String> register(Teacher teacher);
}
