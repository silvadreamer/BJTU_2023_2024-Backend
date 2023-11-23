package com.bjtu.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Code
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("content")
    private String content;
    @TableField("student_number")
    private String studentNumber;
    @TableField("code_homework_id")
    private Integer codeHomeworkId;
}
