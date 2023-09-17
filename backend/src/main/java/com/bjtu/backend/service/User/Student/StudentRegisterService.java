package com.bjtu.backend.service.User.Student;

import com.bjtu.backend.pojo.Users.Student;

import java.util.Map;

public interface StudentRegisterService
{
    Map<String, String> register(Student stu);
}
