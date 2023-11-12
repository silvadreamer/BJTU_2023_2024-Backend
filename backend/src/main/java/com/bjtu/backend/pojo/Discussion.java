package com.bjtu.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discussion
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("student_number")
    private String studentNumber;
    @TableField("teacher_number")
    private String teacherNumber;
    @TableField("content")
    private String content;
    @TableField("homework_id")
    private Integer homeworkId;
    @TableField("deleted")
    private Integer deleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;
    private Integer reply;
}
