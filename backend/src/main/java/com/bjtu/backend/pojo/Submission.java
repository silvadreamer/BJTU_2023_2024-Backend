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
public class Submission
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("student_number")
    private String studentNumber;
    private String status;
    @TableField("code_info_id")
    private Integer codeInfoId;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;
    @TableField("testcase_input")
    private String testcaseInput;
    @TableField("testcase_output")
    private String testcaseOutput;
    @TableField("user_output")
    private String userOutput;
}
