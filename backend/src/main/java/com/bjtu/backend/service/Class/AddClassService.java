package com.bjtu.backend.service.Class;


import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.Users.Student;

import java.util.Map;

public interface AddClassService
{
    Map<String, String> addClass(Class classInfo, Student student);
}
