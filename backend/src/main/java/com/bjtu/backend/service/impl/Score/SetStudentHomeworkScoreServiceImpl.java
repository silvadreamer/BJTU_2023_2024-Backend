package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.mapper.StudentScoreMapper;
import com.bjtu.backend.mapper.TeacherScoreMapper;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.pojo.StudentScore;
import com.bjtu.backend.pojo.TeacherScore;
import com.bjtu.backend.service.Score.SetStudentHomeworkScoreService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import com.mysql.cj.QueryResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Service
public class SetStudentHomeworkScoreServiceImpl implements SetStudentHomeworkScoreService
{
    final HomeworkStudentMapper homeworkStudentMapper;
    final StudentScoreMapper studentScoreMapper;
    final TeacherScoreMapper teacherScoreMapper;

    public SetStudentHomeworkScoreServiceImpl(HomeworkStudentMapper homeworkStudentMapper,
                                              StudentScoreMapper studentScoreMapper,
                                              TeacherScoreMapper teacherScoreMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.teacherScoreMapper = teacherScoreMapper;
        this.studentScoreMapper = studentScoreMapper;
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
}
