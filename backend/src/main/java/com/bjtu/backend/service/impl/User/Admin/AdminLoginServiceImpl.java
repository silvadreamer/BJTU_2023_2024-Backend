package com.bjtu.backend.service.impl.User.Admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.AdminMapper;
import com.bjtu.backend.pojo.Users.Admin;
import com.bjtu.backend.service.User.Admin.AdminLoginService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 管理员登录业务实现
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService
{
    final AdminMapper adminMapper;
    final PasswordEncoder passwordEncoder;
    final RedisTemplate redisTemplate;

    public AdminLoginServiceImpl(AdminMapper adminMapper, PasswordEncoder passwordEncoder, RedisTemplate redisTemplate)
    {
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Map<String, String> login(Admin admin)
    {
        String number = admin.getUsername();
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", number);
        Admin admin1 = adminMapper.selectOne(queryWrapper);

        if(admin1 != null && passwordEncoder.matches(admin.getPassword(), admin1.getPassword()))
        {
            String key = "user" + UUID.randomUUID();

            redisTemplate.opsForValue().set(key, admin1, 60, TimeUnit.MINUTES);

            Map<String, String> data = new HashMap<>();
            data.put("number", number);
            data.put("token", key);

            System.out.println(TimeGenerateUtil.getTime() + " admin login " + number);

            return data;
        }

        return null;
    }
}
