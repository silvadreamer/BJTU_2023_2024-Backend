package com.bjtu.backend.service.User.Student;


import com.bjtu.backend.pojo.Users.Student;

import java.util.Map;

public interface StudentChangePwdService
{
    Map<String, String> changePWD(Student student);
}
