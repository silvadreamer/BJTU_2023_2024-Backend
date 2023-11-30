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
@NoArgsConstructor
@AllArgsConstructor
public class Malicious
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("homework_id")
    private Integer homeworkId;
    @TableField("student_number")
    private String studentNumber;
    @TableField("homework_student_id")
    private Integer homeworkStudentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;
    private String content;
    @TableField("student_score_id")
    private Integer studentScoreId;
}
