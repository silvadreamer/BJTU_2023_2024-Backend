package com.bjtu.backend.controller.User.Student;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.pojo.Users.Student;
import com.bjtu.backend.service.User.Student.StudentDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for student delete service
 */
@RestController
@RequestMapping("/student")
public class StudentDeleteController
{
    final StudentDeleteService studentDeleteService;

    public StudentDeleteController(StudentDeleteService studentDeleteService)
    {
        this.studentDeleteService = studentDeleteService;
    }

    @PostMapping("/delete")
    public Result<Map<String, String>> delete(@RequestBody Student student)
    {
        Map<String, String> map = studentDeleteService.delete(student);

        return Result.success(map);
    }
}
