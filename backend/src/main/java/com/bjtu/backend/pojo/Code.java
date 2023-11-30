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
public class Code
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("content")
    private String content;
    @TableField("student_number")
    private String studentNumber;
    @TableField("code_info_id")
    private Integer codeInfoId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;
}
