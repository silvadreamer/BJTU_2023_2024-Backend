package com.bjtu.backend.mapper.User;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjtu.backend.pojo.Users.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<Admin>
{
}
