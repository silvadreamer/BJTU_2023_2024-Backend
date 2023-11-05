package com.bjtu.backend.controller.Homework;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Homework.DeleteHomeworkService;
import com.bjtu.backend.service.OSS.OSSDeleteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/homework")
public class DeleteHomeworkController
{
    final DeleteHomeworkService deleteHomeworkService;
    final OSSDeleteService ossDeleteService;

    public DeleteHomeworkController(DeleteHomeworkService deleteHomeworkService,
                                    OSSDeleteService ossDeleteService)
    {
        this.deleteHomeworkService = deleteHomeworkService;
        this.ossDeleteService = ossDeleteService;
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam int id)
    {
        deleteHomeworkService.delete(id);

        return Result.success("success");
    }

    /**
     * 删除附件
     * @param id 作业id
     * @param classID 课堂id
     * @param fileName 文件名
     * @return Result
     */
    @PostMapping("/deleteTeacherFile")
    public Result<?> deleteTeacherFile(@RequestParam int id, @RequestParam int classID,
                                       @RequestParam String fileName)
    {
        String prefix = classID + "/" + id;
        boolean isDeleted = ossDeleteService.deleteHomeworkFile(prefix, fileName);

        if(!isDeleted)
        {
            return Result.fail(20001, "删除失败！请稍后重试或联系管理员！");
        }

        deleteHomeworkService.deleteFileName(id, fileName);

        return Result.success(fileName);
    }

    @PostMapping("/deleteStudentFile")
    public Result<?> deleteStudentFile(@RequestParam int id, @RequestParam int classID,
                                       @RequestParam int homeworkID,@RequestParam String studentID,
                                       @RequestParam String fileName)
    {
        String prefix = classID + "/" + homeworkID + "/" + studentID;
        boolean isDeleted = ossDeleteService.deleteHomeworkFile(prefix, fileName);

        if(!isDeleted)
        {
            return Result.fail(20001, "删除失败！请稍后重试或联系管理员！");
        }

        deleteHomeworkService.deleteFileNameForStudent(id, fileName);

        return Result.success(fileName);

    }
}
