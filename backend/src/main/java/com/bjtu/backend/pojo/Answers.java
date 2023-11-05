package com.bjtu.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Answers
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    private String filename;
    @TableField("homework_id")
    private Integer homeworkId;
}
