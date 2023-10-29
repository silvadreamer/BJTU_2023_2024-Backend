package com.bjtu.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Homework
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("class_id")
    private Integer classId;
    private Date start;
    private Date end;
    private String content;
    private Integer resubmit;
    @TableField("file_name")
    private String fileName;
}
