package com.bjtu.backend.service.impl.Answer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.AnswersMapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.pojo.Answers;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.service.Answers.AddAnswerService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddAnswerServiceImpl implements AddAnswerService
{
    final AnswersMapper answersMapper;
    final HomeworkMapper homeworkMapper;

    public AddAnswerServiceImpl(AnswersMapper answersMapper,
                                HomeworkMapper homeworkMapper)
    {
        this.answersMapper = answersMapper;
        this.homeworkMapper = homeworkMapper;
    }

    @Override
    public Map<String, Object> addAnswer(Answers answers)
    {
        Map<String, Object> map = new HashMap<>();

        int homeworkId = answers.getHomeworkId();
        QueryWrapper<Answers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);

        QueryWrapper<Homework> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", homeworkId);
        Homework homework = homeworkMapper.selectOne(queryWrapper1);
        homework.setAnswer(1);
        homeworkMapper.update(homework, queryWrapper1);

        if(answersMapper.exists(queryWrapper))
        {
            map.put("fail", "答案已存在");
        }
        else
        {
            answersMapper.insert(answers);
            map.put("answer", answers);
        }

        return map;
    }

    @Override
    public Map<String, Object> addFileName(int answerId, String fileName)
    {
        QueryWrapper<Answers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", answerId);

        Answers answers = answersMapper.selectOne(queryWrapper);
        String fileNames = answers.getFilename();
        if(fileNames == null) fileNames = fileName;
        else fileNames = fileNames + "|" + fileName;
        answers.setFilename(fileNames);

        System.out.println(TimeGenerateUtil.getTime() + " 答案附件" + fileName);

        answersMapper.update(answers, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("fileName", fileName);
        return map;
    }
}
