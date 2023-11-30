package com.bjtu.backend.service.impl.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.ExcellentHomeworkMapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkReviewMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.ExcellentHomework;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkReview;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Homework.ShowHomeworkService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class ShowHomeworkServiceImpl implements ShowHomeworkService
{
    final HomeworkMapper homeworkMapper;
    final HomeworkStudentMapper homeworkStudentMapper;
    final ExcellentHomeworkMapper excellentHomeworkMapper;
    final HomeworkReviewMapper homeworkReviewMapper;


    public ShowHomeworkServiceImpl(HomeworkMapper homeworkMapper, HomeworkStudentMapper homeworkStudentMapper,
                                   ExcellentHomeworkMapper excellentHomeworkMapper,
                                   HomeworkReviewMapper homeworkReviewMapper)
    {
        this.homeworkMapper = homeworkMapper;
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.excellentHomeworkMapper = excellentHomeworkMapper;
        this.homeworkReviewMapper = homeworkReviewMapper;
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
    public Map<String, Object> showSubmitted(int classId, int homeworkId, Long pageNo, Long pageSize)
    {

        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classId).eq("homework_id", homeworkId);
        queryWrapper.select("id", "homework_id", "student_number", "class_id", "date", "score");
        Page<HomeworkStudent> page = new Page<>(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>();

        map.put("homeworkInfo", homeworkStudentMapper.selectPage(page, queryWrapper));

        List<HomeworkStudent> homeworkStudentList = homeworkStudentMapper.selectPage(page, queryWrapper).getRecords();

        List<Integer> list = new ArrayList<>();
        for(HomeworkStudent homeworkStudent : homeworkStudentList)
        {
            int homeworkStudentId = homeworkStudent.getId();
            QueryWrapper<ExcellentHomework> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("homework_student_id", homeworkStudentId);
            if(excellentHomeworkMapper.exists(queryWrapper1))
            {
                int id = excellentHomeworkMapper.selectOne(queryWrapper1).getId();
                list.add(id);
            }
            else
            {
                list.add(0);
            }
        }
        map.put("excellent", list);

        System.out.println(TimeGenerateUtil.getTime() + " get submitted homework list ");

        return map;
    }

    @Override
    public Map<String, Object> showForStudent(int classID, int studentID, Long pageNo, Long pageSize)
    {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classID);

        queryWrapper.select("id", "class_id", "end", "resubmit", "name", "start", "end", "discussion", "answer");

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

        map.put("total", valid_homework.size());

        QueryWrapper<HomeworkStudent> queryWrapper1 = new QueryWrapper<>();
        List<Integer> isSubmitted = new ArrayList<>();
        List<Integer> timeValid = new ArrayList<>();
        List<Integer> review = new ArrayList<>();

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
//                Date currentDate = new Date();
//                Date endDate = homework.getEnd();
//
//                //查看作业还能不能交
//                if(currentDate.after(endDate)) isSubmitted.add(-1);
                isSubmitted.add(0);
            }

            Date currentDate = new Date();
            Date endDate = homework.getEnd();
            if(currentDate.after(endDate)) timeValid.add(-1);
            else timeValid.add(0);

            QueryWrapper<HomeworkReview> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("homework_id", homeworkId);
            if(!homeworkReviewMapper.exists(queryWrapper2))
            {
                review.add(0);
            }
            else
            {
                Date startTime = homeworkReviewMapper.selectOne(queryWrapper2).getStart();
                Date endTime = homeworkReviewMapper.selectOne(queryWrapper2).getEnd();

                if(currentDate.before(startTime)) review.add(0);
                else if(currentDate.after(endTime)) review.add(-1);
                else review.add(1);
            }

            queryWrapper1.clear();
        }

        System.out.println(TimeGenerateUtil.getTime() + " student get submitted homework list ");

        map.put("isSubmitted", isSubmitted);
        map.put("timeValid", timeValid);
        map.put("review", review);


        return map;
    }
}
