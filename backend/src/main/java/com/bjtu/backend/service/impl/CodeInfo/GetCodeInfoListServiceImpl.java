package com.bjtu.backend.service.impl.CodeInfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.CodeInfoMapper;
import com.bjtu.backend.pojo.CodeInfo;
import com.bjtu.backend.service.CodeInfo.GetCodeInfoListService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetCodeInfoListServiceImpl implements GetCodeInfoListService
{
    final CodeInfoMapper codeInfoMapper;

    public GetCodeInfoListServiceImpl(CodeInfoMapper codeInfoMapper)
    {
        this.codeInfoMapper = codeInfoMapper;
    }

    @Override
    public Map<String, Object> getListStudent(Long pageNo, Long pageSize)
    {
        Page<CodeInfo> page = new Page<>(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>();
        QueryWrapper<CodeInfo> queryWrapper = new QueryWrapper<>();


        map.put("list", codeInfoMapper.selectPage(page, queryWrapper));

        return map;
    }
}
