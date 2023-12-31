package com.bjtu.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherScore
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("homework_student_id")
    private Integer homeworkStudentId;
    private Integer score;
    private String content;
}
