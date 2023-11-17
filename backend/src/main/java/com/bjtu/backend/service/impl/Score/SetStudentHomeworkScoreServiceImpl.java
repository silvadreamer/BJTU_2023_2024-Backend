package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.*;
import com.bjtu.backend.pojo.*;
import com.bjtu.backend.service.Score.SetStudentHomeworkScoreService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import com.mysql.cj.QueryResult;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class SetStudentHomeworkScoreServiceImpl implements SetStudentHomeworkScoreService
{
    final HomeworkStudentMapper homeworkStudentMapper;
    final StudentScoreMapper studentScoreMapper;
    final TeacherScoreMapper teacherScoreMapper;
    final HomeworkMapper homeworkMapper;
    final HomeworkReviewMapper homeworkReviewMapper;

    public SetStudentHomeworkScoreServiceImpl(HomeworkStudentMapper homeworkStudentMapper,
                                              StudentScoreMapper studentScoreMapper,
                                              TeacherScoreMapper teacherScoreMapper,
                                              HomeworkMapper homeworkMapper,
                                              HomeworkReviewMapper homeworkReviewMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.teacherScoreMapper = teacherScoreMapper;
        this.studentScoreMapper = studentScoreMapper;
        this.homeworkMapper = homeworkMapper;
        this.homeworkReviewMapper = homeworkReviewMapper;
    }

    /**
     * 计算互评得分
     * version 0.1
     * 选取所有得分，去掉最高分和最低分，剩下分取平均。
     * @param homeworkStudentId 获得的学生的作业id
     * @return Map
     */
    @Override
    public Map<String, String> setScore(int homeworkStudentId)
    {
        Map<String, String> map = new HashMap<>();

        int score1 = -1;

        //先获得互评分数
        QueryWrapper<StudentScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_student_id", homeworkStudentId);

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        List<StudentScore> list = studentScoreMapper.selectList(queryWrapper);

        for(StudentScore studentScore : list)
        {
            Integer score = studentScore.getScore();

            if(score == -1)
            {
                continue;
            }

            priorityQueue.add(score);
        }

        Integer total_score = 0;
        int maxi = 0;
        int mini = 1000;

        if(priorityQueue.size() <= 1)
        {
            map.put("学生评分人数", "人数不足2人");
        }
        else if(priorityQueue.size() == 2)
        {
            for(Integer score : priorityQueue)
            {
                total_score += score;
            }
            int student_score = total_score/priorityQueue.size();
            score1 = student_score;
            map.put("学生评分", Integer.toString(student_score));
            map.put("学生评分人数", "2");
        }
        else
        {
            for(Integer score : priorityQueue)
            {
                total_score += score;
                maxi = Math.max(maxi, score);
                mini = Math.min(mini, score);
            }

            int student_score = (total_score - maxi - mini)/(priorityQueue.size()-2);
            score1 = student_score;
            map.put("学生评分", Integer.toString(student_score));
            map.put("学生评分人数", Integer.toString(priorityQueue.size()));
        }

        //获得教师分数
        QueryWrapper<TeacherScore> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("homework_student_id", homeworkStudentId);
        Integer score = -1;
        if(teacherScoreMapper.exists(queryWrapper1))
        {
            score = teacherScoreMapper.selectOne(queryWrapper1).getScore();

            if(score != -1)
            {
                map.put("教师评分", Integer.toString(score));
            }
        }
        else
        {
            map.put("教师评分", "教师尚未评阅");
        }

        UpdateWrapper<HomeworkStudent> updateWrapper = new UpdateWrapper<>();

        if(score == -1 && score1 == -1)
        {
            map.put("学生成绩", "暂无评分");
        }
        else if(score1 == -1)
        {
            updateWrapper.eq("id", homeworkStudentId).set("score", score);
            map.put("学生成绩", Integer.toString(score));
        }
        else if(score == -1)
        {
            updateWrapper.eq("id", homeworkStudentId).set("score", score1);
            map.put("学生成绩", Integer.toString(score1));
        }
        else
        {
            updateWrapper.eq("id", homeworkStudentId).set("score", (int)(score * 0.7 + score1 * 0.3));
            map.put("学生成绩", Integer.toString((int)(score * 0.7 + score1 * 0.3)));
        }
        homeworkStudentMapper.update(null, updateWrapper);

        System.out.println(TimeGenerateUtil.getTime() + " 计算学生作业的成绩");


        return map;
    }

    /**
     * 评分方法基于SABTXT改进
     * [Practical Methods for Semi-automated Peer Grading in a Classroom Setting]
     * 单个学生评分时可能会整体高于或低于教师的评分，且这种幅度是基于单个作业的
     * 原文中将学生所有评判的作业统一计算得出bias，实际上不符合常理
     * 因为每个学生对于不同答案的标准判断不同，偏差幅度也不尽相同
     * 因此，对此方法进行改进，每位同学的每次作业都会有一个bias
     * 通过此偏差来计算最终的得分，教师、同学的权重仍然保留
     * @param homeworkId 作业id编号（不是学生作业id）
     * @return Map
     */
    @Override
    public Map<String, Object> SABTXT_simple(int homeworkId)
    {
        Map<String, Object> map = new HashMap<>();
        Date now = new Date();
        QueryWrapper<HomeworkReview> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("homework_id", homeworkId);
        Date end = homeworkReviewMapper.selectOne(queryWrapper2).getEnd();

        if(end.after(now))
        {
            map.put("error", "请等待作业结束！");
            return map;
        }

        Map<String, Double> bias = new HashMap<>();

        //先计算每个同学的bias
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);
        List<HomeworkStudent> homeworkStudentList = homeworkStudentMapper.selectList(queryWrapper);

        //获得教师的关于本次作业所有同学的评分
        Map<Integer, Integer> teacherScore = new HashMap<>();

        for(HomeworkStudent homeworkStudent : homeworkStudentList)
        {
            int homeworkStudentId = homeworkStudent.getId();
            QueryWrapper<TeacherScore> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("homework_student_id", homeworkStudentId);

            if(!teacherScoreMapper.exists(queryWrapper1))
            {
                map.put("error", "教师有未评分作业！请检查");
                return map;
            }
            else teacherScore.put(homeworkStudentId, teacherScoreMapper.selectOne(queryWrapper1).getScore());
        }

        //获得当前评分表中的本次作业
        QueryWrapper<StudentScore> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("homework_id", homeworkId);
        List<StudentScore> studentScoreList = studentScoreMapper.selectList(queryWrapper1);

        //有效的总数
        Map<String, Integer> D = new HashMap<>();
        //偏差值总和
        Map<String, Integer> sum = new HashMap<>();

        for(StudentScore studentScore : studentScoreList)
        {
            String studentNumber = studentScore.getStudentNumber();

            Integer homeworkStudentId = studentScore.getHomeworkStudentId();
            Integer grade = studentScore.getScore();

            if(grade == -1)
            {
                continue;
            }

            if(sum.containsKey(studentNumber))
            {
                Integer grades = sum.get(studentNumber);
                grades = grades + grade - teacherScore.get(homeworkStudentId);
                sum.replace(studentNumber, grades);
            }
            else
            {
                sum.put(studentNumber, grade - teacherScore.get(homeworkStudentId));
            }

            if(D.containsKey(studentNumber))
            {
                Integer nums = D.get(studentNumber);
                nums ++;
                D.replace(studentNumber, nums);
            }
            else
            {
                D.put(studentNumber, 1);
            }
        }

        //计算bias
        for(String key : sum.keySet())
        {
            Integer totalGrade = sum.get(key);
            Integer nums = D.get(key);

            Double BIAS = (totalGrade * 1.0) / (nums * 1.0);
            bias.put(key, BIAS);
        }

        //获得bias后，就可以计算真实得分，此处同样采取加权，但不去掉学生的最低最高分
        Double studentRate = homeworkReviewMapper.selectOne(queryWrapper2).getStudentRate();
        Double teacherRate = 1 - studentRate;

        for(Integer homeworkStudentId : teacherScore.keySet())
        {
            Integer scoreByTeacher = teacherScore.get(homeworkStudentId);
            double totalScore = 0.0;
            int num = 0;

            for(StudentScore studentScore : studentScoreList)
            {
                Integer hsId = studentScore.getHomeworkStudentId();
                if(!Objects.equals(hsId, homeworkStudentId)) continue;

                String number = studentScore.getStudentNumber();
                totalScore += (studentScore.getScore() * 1.0) - bias.get(number);
                num ++;
            }

            totalScore = totalScore/num * studentRate + scoreByTeacher * teacherRate;
            System.out.println("homeworkStudentID:" + homeworkStudentId);
            System.out.println("teacherScore: " + scoreByTeacher);
            System.out.println("final SCORE : " + totalScore);

            UpdateWrapper<HomeworkStudent> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", homeworkStudentId).set("score", totalScore);
            homeworkStudentMapper.update(null, updateWrapper);

            map.put(Integer.toString(homeworkStudentId), homeworkStudentMapper.selectOne(updateWrapper));

        }

        return map;
    }
}
