package com.bjtu.backend.service.impl.CodeInfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.CodeInfoMapper;
import com.bjtu.backend.pojo.CodeInfo;
import com.bjtu.backend.service.CodeInfo.GetCodeInfoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetCodeInfoServiceImpl implements GetCodeInfoService
{
    final CodeInfoMapper codeInfoMapper;

    public GetCodeInfoServiceImpl(CodeInfoMapper codeInfoMapper)
    {
        this.codeInfoMapper = codeInfoMapper;
    }

    @Override
    public Map<String, Object> getCodeInfo(int id)
    {
        QueryWrapper<CodeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Map<String, Object> map = new HashMap<>();
        map.put("题面", codeInfoMapper.selectOne(queryWrapper));

        return map;
    }
}
