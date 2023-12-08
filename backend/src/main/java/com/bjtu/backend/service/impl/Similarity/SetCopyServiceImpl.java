package com.bjtu.backend.service.impl.Similarity;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.mapper.ReminderMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.pojo.Reminder;
import com.bjtu.backend.service.Similarity.SetCopyService;
import org.springframework.stereotype.Service;

import javax.sql.rowset.spi.SyncResolver;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Service
public class SetCopyServiceImpl implements SetCopyService
{
    final HomeworkStudentMapper homeworkStudentMapper;
    final ReminderMapper reminderMapper;
    final HomeworkMapper homeworkMapper;
    final ClassMapper classMapper;

    public SetCopyServiceImpl(HomeworkStudentMapper homeworkStudentMapper, ReminderMapper reminderMapper, HomeworkMapper homeworkMapper, ClassMapper classMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.reminderMapper = reminderMapper;
        this.homeworkMapper = homeworkMapper;
        this.classMapper = classMapper;
    }


    @Override
    public Map<String, Object> set(int homeworkId, int classId, String studentNumber)
    {
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("homework_id", homeworkId).eq("class_id", classId).eq("student_number", studentNumber);

        HomeworkStudent homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper);

        //将学生作业表的成绩设置为-1
        homeworkStudent.setScore(-1);

        homeworkStudentMapper.update(homeworkStudent, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("copy", homeworkStudent);


        QueryWrapper<Homework> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", homeworkId);
        Homework homework = homeworkMapper.selectOne(queryWrapper1);

        QueryWrapper<Class> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id", classId);
        Class classInfo = classMapper.selectOne(queryWrapper2);


        //添加告知信息
        Reminder reminder = new Reminder();
        reminder.setStudentNumber(studentNumber);
        Date date = new Date();
        reminder.setDate(date);
        reminder.setContent("你的" +  classInfo.getName() + "课程的" + homework.getName() +"作业被判定为抄袭！" );
        reminder.setTitle("抄袭报告！");
        reminderMapper.insert(reminder);

        return map;
    }
}
