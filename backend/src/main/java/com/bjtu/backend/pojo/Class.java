package com.bjtu.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Class
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String info;
    private Integer teacher;
    private String name;
    private Integer num;
    @TableField("current_num")
    private Integer currentNum;
}
