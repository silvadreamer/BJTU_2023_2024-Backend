package com.bjtu.backend.service.User.Student;

import com.bjtu.backend.pojo.Users.Student;

import java.util.Map;

public interface StudentResetPwdService
{
    Map<String, String> ResetPwd(Student student);
}
