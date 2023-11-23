package com.bjtu.backend.service.impl.Reminder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.ReminderMapper;
import com.bjtu.backend.pojo.Reminder;
import com.bjtu.backend.service.Reminder.ShowReminderListService;
import com.bjtu.backend.service.impl.Homework.ShowHomeworkServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowReminderListServiceImpl implements ShowReminderListService
{
    final ReminderMapper reminderMapper;

    public ShowReminderListServiceImpl(ReminderMapper reminderMapper)
    {
        this.reminderMapper = reminderMapper;
    }


    @Override
    public Map<String, Object> showReminder(String studentNumber, Long pageNo, Long pageSize)
    {
        Page<Reminder> page = new Page<>(pageNo, pageSize);

        QueryWrapper<Reminder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number", studentNumber).orderByDesc("id");
        queryWrapper.select("id", "date", "date", "title", "is_read");
        Map<String, Object> map = new HashMap<>();

        map.put("list", reminderMapper.selectPage(page, queryWrapper));

        List<Reminder> list = reminderMapper.selectList(queryWrapper);

        int num = 0;
        for(Reminder reminder : list)
        {
            int read = reminder.getIsRead();
            if(read == 0) num ++;
        }

        map.put("未读", num);

        return map;
    }

    @Override
    public Map<String, Object> showRead(String studentNumber, Long pageNo, Long pageSize)
    {
        Page<Reminder> page = new Page<>(pageNo, pageSize);

        QueryWrapper<Reminder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number", studentNumber).eq("is_read", 1).orderByDesc("id");
        queryWrapper.select("id", "date", "date", "title", "is_read");
        Map<String, Object> map = new HashMap<>();

        map.put("list", reminderMapper.selectPage(page, queryWrapper));

        List<Reminder> list = reminderMapper.selectList(queryWrapper);

        int num = 0;
        for(Reminder reminder : list)
        {
            int read = reminder.getIsRead();
            if(read == 0) num ++;
        }

        map.put("数量", num);

        return map;
    }

    @Override
    public Map<String, Object> showUnRead(String studentNumber, Long pageNo, Long pageSize)
    {
        Page<Reminder> page = new Page<>(pageNo, pageSize);

        QueryWrapper<Reminder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number", studentNumber).eq("is_read", 0).orderByDesc("id");
        queryWrapper.select("id", "date", "date", "title", "is_read");
        Map<String, Object> map = new HashMap<>();

        map.put("list", reminderMapper.selectPage(page, queryWrapper));

        List<Reminder> list = reminderMapper.selectList(queryWrapper);

        int num = 0;
        for(Reminder reminder : list)
        {
            int read = reminder.getIsRead();
            if(read == 0) num ++;
        }

        map.put("数量", num);

        return map;
    }
}
