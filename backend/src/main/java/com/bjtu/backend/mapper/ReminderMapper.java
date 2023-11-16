package com.bjtu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjtu.backend.pojo.Reminder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReminderMapper extends BaseMapper<Reminder>
{
}
