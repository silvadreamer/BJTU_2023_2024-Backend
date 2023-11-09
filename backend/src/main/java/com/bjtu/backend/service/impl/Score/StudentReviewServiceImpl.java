package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.StudentScoreMapper;
import com.bjtu.backend.pojo.StudentScore;
import com.bjtu.backend.service.Score.StudentReviewService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class StudentReviewServiceImpl implements StudentReviewService
{
    final StudentScoreMapper studentScoreMapper;

    public StudentReviewServiceImpl(StudentScoreMapper studentScoreMapper)
    {
        this.studentScoreMapper = studentScoreMapper;
    }

    /**
     * 获得当前互评人未评分的、评过分的相关id
     * @param homeworkId 互评作业id
     * @param studentNumber 当前互评人id
     * @return Map
     */
    @Override
    public Map<String, Object> getReviewId(int homeworkId, String studentNumber)
    {
        QueryWrapper<StudentScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId).eq("student_number", studentNumber);

        List<StudentScore> list = studentScoreMapper.selectList(queryWrapper);

        List<Integer> reviewed = new ArrayList<>();
        List<Integer> unreviewed = new ArrayList<>();

        List<Integer> reviewedScoreId = new ArrayList<>();
        List<Integer> unreviewedScoreId = new ArrayList<>();

        for(StudentScore studentScore : list)
        {
            Integer id = studentScore.getHomeworkStudentId();
            Integer scoreId = studentScore.getId();

            if(studentScore.getScore() != -1)
            {
                reviewed.add(id);
                reviewedScoreId.add(scoreId);
            }
            else
            {
                unreviewed.add(id);
                unreviewedScoreId.add(scoreId);
            }
        }

        System.out.println(TimeGenerateUtil.getTime() + " 获得学生的互评列表");

        Map<String, Object> map = new HashMap<>();
        map.put("未评分-作业表", unreviewed);
        map.put("未评分-评分表", unreviewedScoreId);
        map.put("评分-作业表", reviewed);
        map.put("评分-评分表", reviewedScoreId);

        return map;
    }


    /**
     * 学生评分
     * @param id 评分表中的id
     * @param score 得分
     * @return score
     */
    @Override
    public Map<String, String> StudentReview(int id, int score)
    {
        UpdateWrapper<StudentScore> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("score", score);

        studentScoreMapper.update(null, updateWrapper);

        System.out.println(TimeGenerateUtil.getTime() + " 学生评分");

        Map<String, String> map = new HashMap<>();

        map.put("评分", Integer.toString(score));

        return map;
    }
}
