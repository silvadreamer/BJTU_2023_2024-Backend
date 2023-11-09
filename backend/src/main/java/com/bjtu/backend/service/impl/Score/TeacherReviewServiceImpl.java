package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.TeacherScoreMapper;
import com.bjtu.backend.pojo.TeacherScore;
import com.bjtu.backend.service.Score.TeacherReviewService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherReviewServiceImpl implements TeacherReviewService
{
    final TeacherScoreMapper teacherScoreMapper;

    public TeacherReviewServiceImpl(TeacherScoreMapper teacherScoreMapper)
    {
        this.teacherScoreMapper = teacherScoreMapper;
    }


    @Override
    public Map<String, Object> getReviewId(int homeworkId, String studentNumber)
    {
        return null;
    }

    /**
     * 教师评分
     * @param score 得分
     * @param homeworkStudentId id
     * @return Map
     */
    @Override
    public Map<String, Object> teacherReview(int score, int homeworkStudentId)
    {
        QueryWrapper<TeacherScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_student_id", homeworkStudentId);

        Map<String, Object> map = new HashMap<>();

        if(teacherScoreMapper.exists(queryWrapper))
        {
            UpdateWrapper<TeacherScore> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("homework_student_id", homeworkStudentId).set("score", score);
            teacherScoreMapper.update(null, updateWrapper);
            map.put("得分", Integer.toString(score));
        }
        else
        {
            TeacherScore teacherScore = new TeacherScore();
            teacherScore.setScore(score);
            teacherScore.setHomeworkStudentId(homeworkStudentId);
            teacherScoreMapper.insert(teacherScore);
            map.put("得分", Integer.toString(score));
        }

        queryWrapper.clear();
        queryWrapper.eq("homework_student_id", homeworkStudentId);
        map.put("score", teacherScoreMapper.selectOne(queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " 教师评分");

        return map;
    }
}
