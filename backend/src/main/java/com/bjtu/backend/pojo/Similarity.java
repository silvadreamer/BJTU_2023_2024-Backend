package com.bjtu.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Similarity
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("student_number")
    private String studentNumber;
    @TableField("class_id")
    private Integer classId;
    @TableField("homework_id")
    private Integer homeworkId;
    private String content;
}
