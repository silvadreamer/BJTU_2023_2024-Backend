package com.bjtu.backend.service.impl.Answer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.AnswersMapper;
import com.bjtu.backend.pojo.Answers;
import com.bjtu.backend.service.Answers.GetAnswerService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class GetAnswerServiceImpl implements GetAnswerService
{
    final AnswersMapper answersMapper;

    public GetAnswerServiceImpl(AnswersMapper answersMapper)
    {
        this.answersMapper = answersMapper;
    }

    @Override
    public Map<String, Object> get(int id)
    {
        QueryWrapper<Answers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", id);

        Map<String, Object> map = new HashMap<>();

        if(!answersMapper.exists(queryWrapper))
        {
            map.put("answer", "暂无答案");
            return map;
        }

        Answers answers = answersMapper.selectOne(queryWrapper);
        map.put("answer", answers);

        String files = answers.getFilename();
        HashSet<String> uniqueParts = new HashSet<>();

        if(files != null)
        {
            String[] parts = files.split("\\|");

            for (String part : parts)
            {
                uniqueParts.add(part.trim());
            }

        }
        String[] uniquePartsArray = uniqueParts.toArray(new String[0]);

        map.put("files", uniquePartsArray);

        System.out.println(TimeGenerateUtil.getTime() + " 获得答案信息");

        return map;
    }
}
