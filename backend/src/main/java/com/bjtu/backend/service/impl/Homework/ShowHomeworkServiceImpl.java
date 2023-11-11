package com.bjtu.backend.service.impl.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Homework.ShowHomeworkService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShowHomeworkServiceImpl implements ShowHomeworkService
{
    final HomeworkMapper homeworkMapper;
    final HomeworkStudentMapper homeworkStudentMapper;

    public ShowHomeworkServiceImpl(HomeworkMapper homeworkMapper, HomeworkStudentMapper homeworkStudentMapper)
    {
        this.homeworkMapper = homeworkMapper;
        this.homeworkStudentMapper = homeworkStudentMapper;
    }


    @Override
    public Map<String, Object> show(Page<Homework> page, QueryWrapper<Homework> queryWrapper)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("homeworkInfo", homeworkMapper.selectPage(page, queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " get homework list ");

        return map;
    }

    @Override
    public Map<String, Object> showSubmitted(Page<HomeworkStudent> page, QueryWrapper<HomeworkStudent> queryWrapper)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("homeworkInfo", homeworkStudentMapper.selectPage(page, queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " get submitted homework list ");

        return map;
    }

    @Override
    public Map<String, Object> showForStudent(int classID, int studentID, Long pageNo, Long pageSize)
    {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classID);

        queryWrapper.select("id", "class_id", "end", "resubmit", "name", "start", "end");

        Page<Homework> page = new Page<>(pageNo, pageSize);

        //获得原始的课程列表
        List<Homework> origin_homework = homeworkMapper.selectPage(page, queryWrapper).getRecords();
        List<Homework> valid_homework = new ArrayList<>();

        for(Homework homework : origin_homework)
        {
            Date startDate = homework.getStart();
            Date currentDate = new Date();

            if(startDate.before(currentDate))
            {
                valid_homework.add(homework);
            }
        }

        //map.put("homeworkInfo", homeworkMapper.selectPage(page, queryWrapper));
        map.put("homeworkInfo", valid_homework);

        QueryWrapper<HomeworkStudent> queryWrapper1 = new QueryWrapper<>();
        List<Integer> isSubmitted = new ArrayList<>();

        //查看作业是否提交
        //List<Homework> homeworkList = homeworkMapper.selectPage(page, queryWrapper).getRecords();
        for (Homework homework : valid_homework)
        {
            int homeworkId = homework.getId();
            int classId = homework.getClassId();

            queryWrapper1.eq("class_id", classId).eq("homework_id", homeworkId)
                    .eq("student_number", studentID);
            if(homeworkStudentMapper.exists(queryWrapper1))
            {
                HomeworkStudent homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper1);
                isSubmitted.add(homeworkStudent.getId());
            }
            else
            {
                Date currentDate = new Date();
                Date endDate = homework.getEnd();

                //查看作业还能不能交
                if(currentDate.after(endDate)) isSubmitted.add(-1);
                else isSubmitted.add(0);
            }
            queryWrapper1.clear();
        }

        System.out.println(TimeGenerateUtil.getTime() + " student get submitted homework list ");

        map.put("isSubmitted", isSubmitted);


        return map;
    }
}
