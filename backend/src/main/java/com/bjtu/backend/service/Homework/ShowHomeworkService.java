package com.bjtu.backend.service.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;

import java.util.Map;

public interface ShowHomeworkService
{
    /**
     * 查看指定课程的作业列表
     * @param page page
     * @param queryWrapper queryWrapper
     * @return Map
     */
    Map<String, Object> show(Page<Homework> page, QueryWrapper<Homework> queryWrapper);

    Map<String, Object> showSubmitted(int classId, int homeworkId, Long pageNo, Long pageSize);

    Map<String, Object> showUnSubmitted(int classId, int homeworkId, Long pageNo, Long pageSize);

    Map<String, Object> showForStudent(int classID, int studentID, Long pageNo, Long pageSize);
}
