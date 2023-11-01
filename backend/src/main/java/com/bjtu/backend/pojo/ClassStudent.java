package com.bjtu.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassStudent
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("class_id")
    private Integer classId;
    @TableField("student_id")
    private String studentId;

}
