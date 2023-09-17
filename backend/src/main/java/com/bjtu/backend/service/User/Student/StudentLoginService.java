package com.bjtu.backend.service.User.Student;


import com.bjtu.backend.pojo.Users.Student;

import java.util.Map;

public interface StudentLoginService
{
    Map<String, String> login(Student stu);
}
