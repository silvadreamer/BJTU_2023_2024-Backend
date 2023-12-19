package com.bjtu.backend.service.Code;


import java.util.Map;

public interface CodeCheckService
{
    Map<String, Object> check(int id);

    Map<String, Object> JPlagCheck(int id);

    Map<String, Object> ac(int id);
}
