package com.bjtu.backend.service.impl.Reminder;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.mapper.MaliciousMapper;
import com.bjtu.backend.mapper.ReminderMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.pojo.Malicious;
import com.bjtu.backend.pojo.Reminder;
import com.bjtu.backend.service.Reminder.ScoreReminderService;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScoreReminderServiceImpl implements ScoreReminderService
{
    final ReminderMapper reminderMapper;
    final MaliciousMapper maliciousMapper;
    final HomeworkStudentMapper homeworkStudentMapper;
    final HomeworkMapper homeworkMapper;

    public ScoreReminderServiceImpl(ReminderMapper reminderMapper, MaliciousMapper maliciousMapper,
                                    HomeworkStudentMapper homeworkStudentMapper,
                                    HomeworkMapper homeworkMapper) {
        this.reminderMapper = reminderMapper;
        this.maliciousMapper = maliciousMapper;
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.homeworkMapper = homeworkMapper;
    }


    @Override
    public Map<String, Object> remindScore(int maliciousId)
    {
        Reminder reminder = new Reminder();
        QueryWrapper<Malicious> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", maliciousId);
        Malicious malicious = maliciousMapper.selectOne(queryWrapper);
        Date now = new Date();

        QueryWrapper<HomeworkStudent> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", malicious.getHomeworkStudentId());
        HomeworkStudent homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper1);
        int homeworkId = homeworkStudent.getHomeworkId();
        QueryWrapper<Homework> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id", homeworkId);
        Homework homework = homeworkMapper.selectOne(queryWrapper2);
        String title = homework.getName();

        String content = "你对作业" + title + "的互评作业编号" + malicious.getStudentScoreId() + "被判定为恶意评分！请认真互评！";

        reminder.setDate(now);
        reminder.setContent(content);
        reminder.setTitle("互评提醒");
        reminder.setStudentNumber(malicious.getStudentNumber());
        reminderMapper.insert(reminder);

        Map<String, Object> map = new HashMap<>();
        map.put("提醒", reminder);

        return map;
    }
}
