package com.bjtu.backend.service.User.Admin;

import com.bjtu.backend.pojo.Users.Admin;

import java.util.Map;

public interface AdminLoginService
{
    Map<String, String> login(Admin admin);
}
