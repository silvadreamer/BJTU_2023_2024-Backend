package com.bjtu.backend.service.impl.Reminder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.*;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.ClassStudent;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.pojo.Reminder;
import com.bjtu.backend.service.Reminder.HomeworkRemindService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HomeworkRemindServiceImpl implements HomeworkRemindService
{
    final ReminderMapper reminderMapper;
    final HomeworkStudentMapper homeworkStudentMapper;
    final StudentMapper studentMapper;
    final HomeworkMapper homeworkMapper;
    final ClassStudentMapper classStudentMapper;
    final ClassMapper classMapper;


    public HomeworkRemindServiceImpl(ReminderMapper reminderMapper,
                                     HomeworkStudentMapper homeworkStudentMapper,
                                     StudentMapper studentMapper,
                                     HomeworkMapper homeworkMapper,
                                     ClassStudentMapper classStudentMapper,
                                     ClassMapper classMapper)
    {
        this.reminderMapper = reminderMapper;
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.studentMapper = studentMapper;
        this.homeworkMapper = homeworkMapper;
        this.classStudentMapper = classStudentMapper;
        this.classMapper = classMapper;
    }

    @Override
    public Map<String, Object> remindHomework(int homeworkId)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", homeworkId);
        int classId = homeworkMapper.selectOne(queryWrapper).getClassId();
        String name = homeworkMapper.selectOne(queryWrapper).getName();

        //先获得选择这个课程的学生名单
        QueryWrapper<ClassStudent> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("class_id", classId);
        List<ClassStudent> un_submitStudent = classStudentMapper.selectList(queryWrapper1);

        //再获得当前作业提交了作业的学生名单
        QueryWrapper<HomeworkStudent> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("homework_id", homeworkId);
        List<HomeworkStudent> submittedStudent = homeworkStudentMapper.selectList(queryWrapper2);

        //剩下的即为未交作业的学生
        List<String> numbers = new ArrayList<>();

        for(ClassStudent classStudent : un_submitStudent)
        {
            String number = classStudent.getStudentId();
            boolean flag = true;

            for(HomeworkStudent homeworkStudent : submittedStudent)
            {
                String submitNumber = homeworkStudent.getStudentNumber();
                if(submitNumber.equals(number))
                {
                    flag = false;
                    break;
                }
            }

            if(flag) numbers.add(number);
        }

        QueryWrapper<Class> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("id", classId);
        String className = classMapper.selectOne(queryWrapper3).getName();

        for(String studentNumber : numbers)
        {
            Date now = new Date();
            Reminder reminder = new Reminder();
            reminder.setContent("请注意提交课程" + className + "的作业" + name);
            reminder.setStudentNumber(studentNumber);
            reminder.setDate(now);

            reminderMapper.insert(reminder);
            map.put(studentNumber, reminder);
        }

        return map;
    }
}
