package com.bjtu.backend.pojo.Users;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String number;
    private String password;
}
