package com.bjtu.backend.service.impl.Submission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.SubmissionMapper;
import com.bjtu.backend.pojo.Submission;
import com.bjtu.backend.service.Submission.GetSubmissionListService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetSubmissionListServiceImpl implements GetSubmissionListService
{

    final SubmissionMapper submissionMapper;

    public GetSubmissionListServiceImpl(SubmissionMapper submissionMapper)
    {
        this.submissionMapper = submissionMapper;
    }

    @Override
    public Map<String, Object> getSubmissionListForStudent(String studentNumber, int codeInfoId, Long pageNo, Long pageSize)
    {
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number", studentNumber).eq("code_info_id", codeInfoId).orderByDesc("id");

        Page<Submission> page = new Page<>(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("list", submissionMapper.selectPage(page, queryWrapper));

        return map;
    }
}
