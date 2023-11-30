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
public class CodeInfo
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("acw_id")
    private Integer acwId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date end;
    @TableField("input_format")
    private String inputFormat;
    @TableField("output_format")
    private String outputFormat;
    @TableField("input_case")
    private String inputCase;
    @TableField("output_case")
    private String outputCase;
    @TableField("num_range")
    private String numRange;
    private String content;
    private String title;
}
