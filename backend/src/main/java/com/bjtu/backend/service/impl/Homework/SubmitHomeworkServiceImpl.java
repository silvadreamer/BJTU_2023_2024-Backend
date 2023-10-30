package com.bjtu.backend.service.impl.Homework;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Homework.SubmitHomeworkService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubmitHomeworkServiceImpl implements SubmitHomeworkService
{
    final HomeworkStudentMapper homeworkStudentMapper;

    public SubmitHomeworkServiceImpl(HomeworkStudentMapper homeworkStudentMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
    }

    @Override
    public Map<String, Object> submit(HomeworkStudent homeworkStudent)
    {
        //先判断是否已经提交过了
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkStudent.getHomeworkId())
                .eq("class_id", homeworkStudent.getClassId())
                .eq("student_number", homeworkStudent.getStudentNumber());

        Map<String, Object> map = new HashMap<>();

        if(homeworkStudentMapper.exists(queryWrapper))
        {
            homeworkStudentMapper.update(homeworkStudent, queryWrapper);

            queryWrapper.clear();
            queryWrapper.eq("homework_id", homeworkStudent.getHomeworkId())
                    .eq("class_id", homeworkStudent.getClassId())
                    .eq("student_number", homeworkStudent.getStudentNumber());
            homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper);

            map.put("homework", homeworkStudent);
            return map;
        }


        //设置为提交
        homeworkStudent.setSubmit(1);
        homeworkStudentMapper.insert(homeworkStudent);

        System.out.println(TimeGenerateUtil.getTime() + " submit homework");

        queryWrapper.clear();
        queryWrapper.eq("homework_id", homeworkStudent.getHomeworkId())
                .eq("class_id", homeworkStudent.getClassId())
                .eq("student_number", homeworkStudent.getStudentNumber());
        homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper);
        map.put("homework", homeworkStudent);

        return map;
    }

    /**
     * 向学生作业表中添加附件名称
     * @param id 表中序号
     * @param fileName 文件名
     */
    @Override
    public void addFileName(int id, String fileName)
    {
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        HomeworkStudent homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper);
        String fileNames = homeworkStudent.getFileName();
        if(fileNames == null) fileNames = fileName;
        else fileNames = fileNames + "|" + fileName;
        homeworkStudent.setFileName(fileNames);

        System.out.println(TimeGenerateUtil.getTime() + " 学生添加附件" + fileName);

        homeworkStudentMapper.update(homeworkStudent, queryWrapper);
    }
}
