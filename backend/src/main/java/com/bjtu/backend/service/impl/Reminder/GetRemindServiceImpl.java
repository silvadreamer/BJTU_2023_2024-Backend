package com.bjtu.backend.service.impl.Reminder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.ReminderMapper;
import com.bjtu.backend.pojo.Reminder;
import com.bjtu.backend.service.Reminder.GetRemindService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetRemindServiceImpl implements GetRemindService
{
    final ReminderMapper reminderMapper;

    public GetRemindServiceImpl(ReminderMapper reminderMapper)
    {
        this.reminderMapper = reminderMapper;
    }

    @Override
    public Map<String, Object> getInfo(int id)
    {
        QueryWrapper<Reminder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Map<String, Object> map = new HashMap<>();

        Reminder reminder = reminderMapper.selectOne(queryWrapper);
        reminder.setIsRead(1);
        map.put("info", reminder);

        reminderMapper.update(reminder, queryWrapper);

        return map;
    }
}
