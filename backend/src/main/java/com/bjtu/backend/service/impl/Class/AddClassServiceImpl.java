package com.bjtu.backend.service.impl.Class;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjtu.backend.mapper.ClassMapper;
import com.bjtu.backend.mapper.ClassStudentMapper;
import com.bjtu.backend.pojo.Class;
import com.bjtu.backend.pojo.ClassStudent;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.Class.AddClassService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AddClassServiceImpl implements AddClassService
{
    final ClassStudentMapper classStudentMapper;
    final ClassMapper classMapper;

    public AddClassServiceImpl(ClassStudentMapper classStudentMapper,
                               ClassMapper classMapper)
    {
        this.classStudentMapper = classStudentMapper;
        this.classMapper = classMapper;
    }



    /**
     * 学生选择课程，class表对应课程加一，选课信息添加新纪录
     * @param classInfo 课程
     * @param student 选课学生
     * @return map
     */
    @Override
    public Map<String, String> addClass(Class classInfo, Student student)
    {
        Map<String, String> map = new HashMap<>();

        int totalNum = classInfo.getNum();
        int currentNum = classInfo.getCurrentNum();
        int studentID = student.getId();
        int classID = classInfo.getId();

        // 课容量判断
        if(totalNum == currentNum)
        {
            map.put("status", "课程已满");
            return map;
        }

        // 判断学生是否已经选过
        QueryWrapper<ClassStudent> classStudentQueryWrapper = new QueryWrapper<>();
        classStudentQueryWrapper.eq("student_id", studentID).eq("class_id", classID);
        if(classStudentMapper.selectOne(classStudentQueryWrapper) != null)
        {
            map.put("status", "课程已存在");
            return map;
        }

        // 更新两个表的内容
        ClassStudent classStudent = new ClassStudent(null, classID, studentID);
        UpdateWrapper<Class> classUpdateWrapper = new UpdateWrapper<>();
        classUpdateWrapper.eq("id", classID).setSql("current_num = current_num + 1");
        classMapper.update(null, classUpdateWrapper);
        classStudentMapper.insert(classStudent);
        map.put("status", "选择成功");

        System.out.println("debug: 选课, " + currentNum);

        return map;
    }
}
