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
public class HomeworkStudent
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String answer;
    @TableField("homework_id")
    private Integer homeworkId;
    @TableField("student_number")
    private String studentNumber;
    @TableField("class_id")
    private Integer classId;
    private Integer submit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;
    @TableField("file_name")
    private String fileName;
    private Integer score;
}
