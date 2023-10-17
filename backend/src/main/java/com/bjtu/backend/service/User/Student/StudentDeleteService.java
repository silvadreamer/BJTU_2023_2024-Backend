package com.bjtu.backend.service.User.Student;

import com.bjtu.backend.pojo.Users.Student;

import java.util.Map;

/**
 * @author Silva31
 * @version 1.0
 * @date 2023/10/17 10:52
 */
public interface StudentDeleteService
{
    Map<String, String> delete(Student student);
}
