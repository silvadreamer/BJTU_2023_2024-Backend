package com.bjtu.backend.service.impl.File;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.TeacherMapper;
import com.bjtu.backend.pojo.Users.Teacher;
import com.bjtu.backend.service.File.TeacherFileService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherFileServiceImpl implements TeacherFileService
{
    final PasswordEncoder passwordEncoder;
    final TeacherMapper teacherMapper;

    public TeacherFileServiceImpl(PasswordEncoder passwordEncoder,
                                  TeacherMapper teacherMapper)
    {
        this.teacherMapper = teacherMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Map<String, Object> teacherFile(MultipartFile multipartFile)
    {
        int successNum = 0;
        int failNum = 0;
        Map<String, Object> map = new HashMap<>();

        try
        {
            Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<Teacher> list = new ArrayList<>();

            int index = 0;

            for(Row row : sheet)
            {
                index ++;
                if(index == 1) continue;

                Cell cell1 = row.getCell(0);
                Cell cell2 = row.getCell(1);
                String column1Value = String.valueOf((int)cell1.getNumericCellValue());
                String column2Value = cell2.getStringCellValue();

                Teacher teacher = new Teacher();
                teacher.setName(column2Value);
                teacher.setNumber(column1Value);
                teacher.setPassword(passwordEncoder.encode(column1Value));

                QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("number", column1Value);

                if(!teacherMapper.exists(queryWrapper))
                {
                    teacherMapper.insert(teacher);
                    successNum ++;
                    list.add(teacher);
                }
                else failNum ++;

            }

            map.put("成功人数", successNum);
            map.put("失败人数", failNum);
            map.put("总人数", successNum + failNum);
            map.put("info", list);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return map;
    }
}
