package com.bjtu.backend.service.impl.Homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkMapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.pojo.Homework;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.service.Homework.DeleteHomeworkService;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;

@Service
public class DeleteHomeworkServiceImpl implements DeleteHomeworkService
{
    final HomeworkMapper homeworkMapper;
    final HomeworkStudentMapper homeworkStudentMapper;

    public DeleteHomeworkServiceImpl(HomeworkMapper homeworkMapper,
                                     HomeworkStudentMapper homeworkStudentMapper)
    {
        this.homeworkMapper = homeworkMapper;
        this.homeworkStudentMapper = homeworkStudentMapper;
    }

    @Override
    public Map<String, String> delete(int id)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        homeworkMapper.delete(queryWrapper);

        System.out.println(TimeGenerateUtil.getTime() + " 教师删除作业");

        return null;
    }

    @Override
    public void deleteFileName(int id, String File)
    {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Homework homework = homeworkMapper.selectOne(queryWrapper);
        String files = homework.getFileName();
        HashSet<String> uniqueParts = new HashSet<>();
        if(files != null)
        {
            String[] parts = files.split("\\|");

            for (String part : parts)
            {
                if(!part.equals(File)) uniqueParts.add(part.trim());
            }
        }

        String fileNames = "";
        for(String fileName : uniqueParts)
        {
            fileNames += "|" + fileName;
        }

        fileNames = fileNames.substring(1);

        homework.setFileName(fileNames);

        homeworkMapper.update(homework, queryWrapper);

        System.out.println(TimeGenerateUtil.getTime() + " 教师删除附件" + File);

    }

    @Override
    public void deleteFileNameForStudent(int id, String File)
    {
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        HomeworkStudent homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper);
        String files = homeworkStudent.getFileName();
        HashSet<String> uniqueParts = new HashSet<>();
        if(files != null)
        {
            String[] parts = files.split("\\|");

            for (String part : parts)
            {
                if(!part.equals(File)) uniqueParts.add(part.trim());
            }
        }

        String fileNames = "";
        for(String fileName : uniqueParts)
        {
            fileNames += "|" + fileName;
        }

        fileNames = fileNames.substring(1);

        homeworkStudent.setFileName(fileNames);

        homeworkStudentMapper.update(homeworkStudent, queryWrapper);

        System.out.println(TimeGenerateUtil.getTime() + " 学生删除附件" + File);
    }
}
