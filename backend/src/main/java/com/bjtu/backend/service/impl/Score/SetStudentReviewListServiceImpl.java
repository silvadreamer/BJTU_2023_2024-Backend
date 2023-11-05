package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkReviewMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.mapper.StudentScoreMapper;
import com.bjtu.backend.pojo.HomeworkReview;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.pojo.StudentScore;
import com.bjtu.backend.service.Score.SetStudentReviewListService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SetStudentReviewListServiceImpl implements SetStudentReviewListService
{
    final HomeworkStudentMapper homeworkStudentMapper;
    final StudentScoreMapper studentScoreMapper;
    final HomeworkReviewMapper homeworkReviewMapper;

    public SetStudentReviewListServiceImpl(HomeworkStudentMapper homeworkStudentMapper,
                                           StudentScoreMapper studentScoreMapper,
                                           HomeworkReviewMapper homeworkReviewMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.studentScoreMapper = studentScoreMapper;
        this.homeworkReviewMapper = homeworkReviewMapper;
    }


    /**
     * version 0.1
     * 老师在作业结束后，分配作业至学生
     * 这个方法是用于对当前指定作业提交的同学进行分配作业，将分配的列表插入到student_score表中
     * 分配规则:
     *  设当前作业完成人数为x
     *  1.x <= 10，每个人评分其余x-1的作业
     *  2.x > 10，每个人评分10个作业，保证每个人的作业被评分十次
     * @param homeworkId 作业id
     * @return Map
     */
    @Override
    public Map<String, String> setReviewList(int homeworkId)
    {
        //判断这次作业是否生成过了
        QueryWrapper<HomeworkReview> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("homework_id", homeworkId);

        Map<String, String> map = new HashMap<>();

        if(homeworkReviewMapper.exists(queryWrapper1))
        {
            map.put("失败", "作业已经分配！");
            return map;
        }

        //先获得提交这次作业的学生list
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);
        List<HomeworkStudent> list =  homeworkStudentMapper.selectList(queryWrapper);

        int num = list.size();
        map.put("当前完成人数", Integer.toString(num));

        if(num <= 10)
        {
            //依次遍历，加入到student_mapper表中
            for(int i = 0; i < num; i ++)
            {
                HomeworkStudent homeworkStudent_x = list.get(i);
                String student_x = homeworkStudent_x.getStudentNumber();
                int homework_student_x = homeworkStudent_x.getId();
                for(int j = i + 1; j < num; j ++)
                {
                    HomeworkStudent homeworkStudent_y = list.get(j);
                    String student_y = homeworkStudent_y.getStudentNumber();
                    int homework_student_y = homeworkStudent_y.getId();

                    //i 改 j 的记录
                    StudentScore studentScore_1 = new StudentScore();
                    studentScore_1.setStudentNumber(student_x);
                    studentScore_1.setHomeworkStudentId(homework_student_y);

                    //j 改 i 的记录
                    StudentScore studentScore_2 = new StudentScore();
                    studentScore_2.setStudentNumber(student_y);
                    studentScore_2.setHomeworkStudentId(homework_student_x);

                    studentScoreMapper.insert(studentScore_1);
                    studentScoreMapper.insert(studentScore_2);
                }
            }
        }
        else
        {
            //每个人往后遍历十个，同时实现双方不重复评分
            for(int i = 0; i < num; i ++)
            {
                HomeworkStudent homeworkStudent_x = list.get(i);
                String student = homeworkStudent_x.getStudentNumber();
                for(int j = 1; j <= 10; j ++)
                {
                    int index = (i + j) % num;
                    HomeworkStudent homeworkStudent_y = list.get(index);
                    int homework_student = homeworkStudent_y.getId();

                    StudentScore studentScore = new StudentScore();
                    studentScore.setStudentNumber(student);
                    studentScore.setHomeworkStudentId(homework_student);

                    studentScoreMapper.insert(studentScore);
                }
            }
        }

        return map;
    }
}
