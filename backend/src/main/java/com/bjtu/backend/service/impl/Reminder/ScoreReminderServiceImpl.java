package com.bjtu.backend.service.impl.Reminder;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.MaliciousMapper;
import com.bjtu.backend.mapper.ReminderMapper;
import com.bjtu.backend.pojo.Malicious;
import com.bjtu.backend.pojo.Reminder;
import com.bjtu.backend.service.Reminder.ScoreReminderService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScoreReminderServiceImpl implements ScoreReminderService
{
    final ReminderMapper reminderMapper;
    final MaliciousMapper maliciousMapper;

    public ScoreReminderServiceImpl(ReminderMapper reminderMapper,
                                    MaliciousMapper maliciousMapper)
    {
        this.reminderMapper = reminderMapper;
        this.maliciousMapper = maliciousMapper;
    }

    @Override
    public Map<String, Object> remindScore(int maliciousId)
    {
        Reminder reminder = new Reminder();
        QueryWrapper<Malicious> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", maliciousId);
        Malicious malicious = maliciousMapper.selectOne(queryWrapper);
        Date now = new Date();

        reminder.setDate(now);
        reminder.setContent(malicious.getContent());
        reminder.setTitle("互评提醒");
        reminder.setStudentNumber(malicious.getStudentNumber());
        reminderMapper.insert(reminder);

        Map<String, Object> map = new HashMap<>();
        map.put("提醒", reminder);

        return map;
    }
}
