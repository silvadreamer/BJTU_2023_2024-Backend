package com.bjtu.backend.service.Class;


import com.bjtu.backend.pojo.Class;

import java.util.Map;

public interface RegisterClassService
{
    Map<String, String> registerClass(Class classInfo);
}
