package com.bjtu.backend.service.impl.File;

import cn.hutool.poi.excel.WorkbookUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.User.StudentMapper;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.File.StudentFileService;
import io.lettuce.core.support.caching.ClientSideCaching;
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
public class StudentFileServiceImpl implements StudentFileService
{

    final PasswordEncoder passwordEncoder;
    final StudentMapper studentMapper;

    public StudentFileServiceImpl(PasswordEncoder passwordEncoder,
                                  StudentMapper studentMapper)
    {
        this.studentMapper = studentMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, Object> studentFile(MultipartFile multipartFile)
    {
        int successNum = 0;
        int failNum = 0;
        Map<String, Object> map = new HashMap<>();

        try
        {
            Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<Student> list = new ArrayList<>();

            int index = 0;

            for(Row row : sheet)
            {
                index ++;
                if(index == 1) continue;

                Cell cell1 = row.getCell(0);
                Cell cell2 = row.getCell(1);
                String column1Value = String.valueOf((int)cell1.getNumericCellValue());
                String column2Value = cell2.getStringCellValue();

                Student student = new Student();
                student.setName(column2Value);
                student.setNumber(column1Value);
                student.setPassword(passwordEncoder.encode(column1Value));

                QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("number", column1Value);

                if(!studentMapper.exists(queryWrapper))
                {
                    studentMapper.insert(student);
                    successNum ++;
                    list.add(student);
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
