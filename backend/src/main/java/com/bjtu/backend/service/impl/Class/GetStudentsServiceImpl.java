package com.bjtu.backend.service.impl.Class;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.backend.mapper.ClassStudentMapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.ClassStudent;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.Class.GetStudentsService;
import com.bjtu.backend.service.impl.User.Student.GetStudentListServiceImpl;
import com.bjtu.backend.utils.TimeGenerateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetStudentsServiceImpl implements GetStudentsService
{
    final ClassStudentMapper classStudentMapper;
    final StudentMapper studentMapper;

    public GetStudentsServiceImpl(ClassStudentMapper classStudentMapper,
                                  StudentMapper studentMapper)
    {
        this.classStudentMapper = classStudentMapper;
        this.studentMapper = studentMapper;
    }

    /**
     * 获得选择课程的学生分页
     * @param classId 课程id
     * @param pageNo 页数
     * @param pageSize 页容量
     * @return Map
     */
    @Override
    public Map<String, Object> getStudents(int classId, Long pageNo, Long pageSize)
    {
        Page<Student> page = new Page<>(pageNo, pageSize);
        QueryWrapper<ClassStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classId);

        Map<String, Object> map = new HashMap<>();

        List<ClassStudent> classStudentList = classStudentMapper.selectList(queryWrapper);

        QueryWrapper<Student> queryWrapper1 = new QueryWrapper<>();

        if(classStudentList.size() != 0)
        {
            for(ClassStudent classStudent : classStudentList)
            {
                String number = classStudent.getStudentId();
                queryWrapper1.or().eq("number", number);
            }

            queryWrapper1.select("number", "name");
        }
        else
        {
            queryWrapper1.eq("number", "00000000");
        }

        map.put("page", studentMapper.selectPage(page, queryWrapper1));

        System.out.println(TimeGenerateUtil.getTime() + " 获得选修课程的学生 ");

        return map;
    }
}
