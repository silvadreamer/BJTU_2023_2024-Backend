package com.bjtu.backend.service.Homework;

import com.bjtu.backend.pojo.Homework;

import java.util.Map;

public interface ModifyHomeworkService
{
    Map<String, String> modify(int id, Homework homework);
}
