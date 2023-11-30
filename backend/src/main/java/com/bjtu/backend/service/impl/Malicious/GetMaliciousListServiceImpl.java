package com.bjtu.backend.service.impl.Malicious;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.MaliciousMapper;
import com.bjtu.backend.pojo.Malicious;
import com.bjtu.backend.service.Malicious.GetMaliciousListService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetMaliciousListServiceImpl implements GetMaliciousListService
{
    final MaliciousMapper maliciousMapper;

    public GetMaliciousListServiceImpl(MaliciousMapper maliciousMapper)
    {
        this.maliciousMapper = maliciousMapper;
    }

    @Override
    public Map<String, Object> getList(int homeworkId, Long pageNo, Long pageSize)
    {
        Page<Malicious> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Malicious> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId);

        Map<String, Object> map = new HashMap<>();
        map.put("list", maliciousMapper.selectPage(page, queryWrapper));

        return map;
    }
}
