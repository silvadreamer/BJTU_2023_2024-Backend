package com.bjtu.backend.service.impl.Score;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.StudentScoreMapper;
import com.bjtu.backend.mapper.TeacherScoreMapper;
import com.bjtu.backend.pojo.StudentScore;
import com.bjtu.backend.pojo.TeacherScore;
import com.bjtu.backend.service.Score.SetContentService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SetContentServiceImpl implements SetContentService
{
    final StudentScoreMapper studentScoreMapper;
    final TeacherScoreMapper teacherScoreMapper;

    public SetContentServiceImpl(StudentScoreMapper studentScoreMapper,
                                 TeacherScoreMapper teacherScoreMapper)
    {
        this.studentScoreMapper = studentScoreMapper;
        this.teacherScoreMapper = teacherScoreMapper;
    }

    /**
     * 学生评语
     * @param content 评语
     * @param id 学生批改的作业在student_score中的id
     * @return Map
     */
    @Override
    public Map<String, Object> studentSetContent(String content, int id)
    {
        UpdateWrapper<StudentScore> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("content", content);

        studentScoreMapper.update(null, updateWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("评语", content);

        System.out.println(TimeGenerateUtil.getTime() + " 学生评语");

        return map;
    }

    /**
     * 教师评语
     * @param content 评语
     * @param id id
     * @return Map
     */
    @Override
    public Map<String, Object> teacherSetContent(String content, int id)
    {
        UpdateWrapper<TeacherScore> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("content", content);

        teacherScoreMapper.update(null, updateWrapper);

        QueryWrapper<TeacherScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Map<String, Object> map = new HashMap<>();
        map.put("object", teacherScoreMapper.selectOne(queryWrapper));

        System.out.println(TimeGenerateUtil.getTime() + " 教师评语");

        return map;
    }
}
