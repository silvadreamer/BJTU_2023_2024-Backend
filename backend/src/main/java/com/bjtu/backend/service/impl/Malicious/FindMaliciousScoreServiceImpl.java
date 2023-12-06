package com.bjtu.backend.service.impl.Malicious;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.mapper.MaliciousMapper;
import com.bjtu.backend.mapper.StudentScoreMapper;
import com.bjtu.backend.mapper.TeacherScoreMapper;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.pojo.Malicious;
import com.bjtu.backend.pojo.StudentScore;
import com.bjtu.backend.pojo.TeacherScore;
import com.bjtu.backend.service.Malicious.FindMaliciousScoreService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FindMaliciousScoreServiceImpl implements FindMaliciousScoreService
{
    final MaliciousMapper maliciousMapper;
    final HomeworkStudentMapper homeworkStudentMapper;
    final TeacherScoreMapper teacherScoreMapper;
    final StudentScoreMapper studentScoreMapper;

    public FindMaliciousScoreServiceImpl(MaliciousMapper maliciousMapper, HomeworkStudentMapper homeworkStudentMapper, TeacherScoreMapper teacherScoreMapper, StudentScoreMapper studentScoreMapper)
    {
        this.maliciousMapper = maliciousMapper;
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.teacherScoreMapper = teacherScoreMapper;
        this.studentScoreMapper = studentScoreMapper;
    }


    @Override
    public Map<String, Object> z_score(int homeworkId, double bias)
    {
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);
        List<HomeworkStudent> homeworkStudentList = homeworkStudentMapper.selectList(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        List<Malicious> list = new ArrayList<>();

        //先删除所有记录
        QueryWrapper<Malicious> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("homework_id", homeworkId);
        maliciousMapper.delete(queryWrapper3);

        //这次作业交的学生
        for(HomeworkStudent homeworkStudent : homeworkStudentList)
        {
            //作业学生号
            int homeworkStudentId = homeworkStudent.getId();

            QueryWrapper<StudentScore> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("homework_student_id", homeworkStudentId);
            List<StudentScore> studentScoreList = studentScoreMapper.selectList(queryWrapper2);

            double miu = 0, sigma = 0;

            for(StudentScore studentScore : studentScoreList)
            {
                int score = studentScore.getScore();
                if(score == -1) continue;
                miu += score;
            }

            miu = miu / studentScoreList.size();


            int count = 0;
            for(StudentScore studentScore : studentScoreList)
            {
                int score = studentScore.getScore();
                if(score == -1) continue;
                sigma += Math.pow(score - miu, 2);
                count ++;
            }

            sigma = Math.pow(sigma/count, 0.5);


            System.out.println(miu);
            System.out.println(sigma);

            //然后计算每个人的偏差值
            for(StudentScore studentScore : studentScoreList)
            {
                int score = studentScore.getScore();
                if(score == -1) continue;
                double z = (score - miu) / sigma;
                if(Math.abs(z) > bias)
                {

                    System.out.println(z);
                    Malicious malicious = new Malicious();

                    malicious.setHomeworkStudentId(studentScore.getHomeworkStudentId());
                    malicious.setHomeworkId(studentScore.getHomeworkId());
                    malicious.setStudentNumber(studentScore.getStudentNumber());
                    malicious.setStudentScoreId(studentScore.getId());

                    QueryWrapper<Malicious> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("student_score_id", studentScore.getStudentNumber());
                    Date now = new Date();
                    malicious.setDate(now);

                    if(maliciousMapper.exists(queryWrapper1))
                    {
                        maliciousMapper.update(malicious, queryWrapper1);
                    }
                    else
                    {
                        maliciousMapper.insert(malicious);
                    }
                    list.add(malicious);
                }
            }
        }

        map.put("疑似恶意", list);
        return map;
    }
}
