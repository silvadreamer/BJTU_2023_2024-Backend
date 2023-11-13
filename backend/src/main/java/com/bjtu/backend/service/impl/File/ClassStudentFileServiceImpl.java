package com.bjtu.backend.service.impl.File;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.mapper.ClassStudentMapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.ClassStudent;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.File.ClassStudentFileService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassStudentFileServiceImpl implements ClassStudentFileService
{
    final ClassStudentMapper classStudentMapper;
    final StudentMapper studentMapper;
    final ClassMapper classMapper;

    public ClassStudentFileServiceImpl(ClassStudentMapper classStudentMapper,
                                       StudentMapper studentMapper,
                                       ClassMapper classMapper)
    {
        this.classStudentMapper = classStudentMapper;
        this.studentMapper = studentMapper;
        this.classMapper = classMapper;
    }

    @Override
    public Map<String, Object> classStudent(MultipartFile multipartFile, int classId)
    {
        int successNum = 0;
        int failNum = 0;
        Map<String, Object> map = new HashMap<>();

        try
        {
            Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<ClassStudent> list = new ArrayList<>();
            List<String> invalidStudent = new ArrayList<>();
            List<ClassStudent> validStudent = new ArrayList<>();

            int index = 0;

            //如果人数超过总人数则全部打回
            UpdateWrapper<Class> queryWrapper2 = new UpdateWrapper<>();
            queryWrapper2.eq("id", classId);
            //当前人数
            int classNowNum = classMapper.selectOne(queryWrapper2).getCurrentNum();
            //总人数
            int totalNum = classMapper.selectOne(queryWrapper2).getNum();

            for(Row row : sheet)
            {
                index ++;
                if(index == 1) continue;

                Cell cell1 = row.getCell(0);
                String column1Value = String.valueOf((int)cell1.getNumericCellValue());

                //判断学生是否存在
                QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("number", column1Value);
                if(!studentMapper.exists(queryWrapper))
                {
                    invalidStudent.add(column1Value);
                    failNum ++;
                    continue;
                }

                //判断是否已经选课
                QueryWrapper<ClassStudent> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("class_id", classId).eq("student_id", column1Value);

                if(classStudentMapper.exists(queryWrapper1))
                {
                   failNum ++;
                   continue;
                }

                ClassStudent classStudent = new ClassStudent();
                classStudent.setClassId(classId);
                classStudent.setStudentId(column1Value);

                validStudent.add(classStudent);
            }

            if(classNowNum + validStudent.size() > totalNum)
            {
                map.put("失败", "课程总容量不足");
                return map;
            }


            for(ClassStudent classStudent : validStudent)
            {
                classStudentMapper.insert(classStudent);
                successNum ++;
            }

            queryWrapper2.set("current_num", classNowNum + validStudent.size());
            classMapper.update(null, queryWrapper2);

            map.put("成功人数", successNum);
            map.put("失败人数", failNum);
            map.put("总人数", successNum + failNum);
            map.put("info", list);
            map.put("不存在的用户", invalidStudent);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return map;
    }
}
