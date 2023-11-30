package com.bjtu.backend.service.impl.Submission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.SubmissionMapper;
import com.bjtu.backend.pojo.Submission;
import com.bjtu.backend.service.Submission.GetSubmissionInfoService;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Service
public class GetSubmissionInfoServiceImpl implements GetSubmissionInfoService
{
    final SubmissionMapper submissionMapper;

    public GetSubmissionInfoServiceImpl(SubmissionMapper submissionMapper)
    {
        this.submissionMapper = submissionMapper;
    }

    @Override
    public Map<String, Object> getInfo(int id)
    {
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Map<String, Object> map = new HashMap<>();
        map.put("info", submissionMapper.selectOne(queryWrapper));

        return map;
    }
}
