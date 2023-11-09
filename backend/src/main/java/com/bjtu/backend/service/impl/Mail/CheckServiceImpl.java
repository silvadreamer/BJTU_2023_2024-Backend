package com.bjtu.backend.service.impl.Mail;

import com.bjtu.backend.service.Mail.CheckService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import io.lettuce.core.ScriptOutputType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService
{
    final RedisTemplate redisTemplate;

    public CheckServiceImpl(RedisTemplate redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean checkCode(String id, String code)
    {
        id = id + "@bjtu.edu.cn";
        String storedCode = (String) redisTemplate.opsForValue().get(id);

        System.out.println(TimeGenerateUtil.getTime() + " " + storedCode + "," + id + "," + code);

        return storedCode != null && storedCode.equals(code);
    }
}
