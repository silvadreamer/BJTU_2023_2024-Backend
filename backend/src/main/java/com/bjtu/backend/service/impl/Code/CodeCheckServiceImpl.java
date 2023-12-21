package com.bjtu.backend.service.impl.Code;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.CodeInfoMapper;
import com.bjtu.backend.mapper.CodeMapper;
import com.bjtu.backend.mapper.ReminderMapper;
import com.bjtu.backend.mapper.SubmissionMapper;
import com.bjtu.backend.pojo.*;
import com.bjtu.backend.service.Code.CodeCheckService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class CodeCheckServiceImpl implements CodeCheckService
{
    final CodeMapper codeMapper;
    final SubmissionMapper submissionMapper;
    final ReminderMapper reminderMapper;
    final CodeInfoMapper codeInfoMapper;

    public CodeCheckServiceImpl(CodeMapper codeMapper, SubmissionMapper submissionMapper, ReminderMapper reminderMapper, CodeInfoMapper codeInfoMapper)
    {
        this.codeMapper = codeMapper;
        this.submissionMapper = submissionMapper;
        this.reminderMapper = reminderMapper;

        this.codeInfoMapper = codeInfoMapper;
    }

    @Override
    public Map<String, Object> check(int id)
    {

        Map<String, Object> map = new HashMap<>();

        deleteFiles();
        var m = generateCpp(id);
        if(m.containsKey("num"))
        {
            m.remove("num");
            map.put("info", m);
        }
        String url = runPerl();
        map.put("url", url);

        return map;
    }

    @Override
    public Map<String, Object> JPlagCheck(int id)
    {
        Map<String, Object> map = new HashMap<>();

        deleteFiles();
        var m = generateCpp(id);
        if(m.containsKey("num"))
        {
            m.remove("num");
            map.put("info", m);
            return map;
        }
        map.put("info", m);
        map.put("jplag", runJPlag());

        return map;
    }

    @Override
    public Map<String, Object> ac(int id)
    {
        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code_info_id", id).select("student_number", "content");
        Map<String, Object> map = new HashMap<>();
        map.put("info", codeMapper.selectList(queryWrapper));
        return map;
    }

    @Override
    public Map<String, Object> setCopy(String studentNumber, int id)
    {
        Reminder reminder = new Reminder();
        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code_info_id", id).eq("student_number", studentNumber);
        Code code = codeMapper.selectOne(queryWrapper);
        Date now = new Date();

        QueryWrapper<Submission> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", code.getSubmissionId());
        Submission submission = submissionMapper.selectOne(queryWrapper1);
        submission.setStatus("被判定为抄袭");
        submissionMapper.update(submission, queryWrapper1);

        QueryWrapper<CodeInfo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id", id);
        CodeInfo codeInfo = codeInfoMapper.selectOne(queryWrapper2);
        String title = codeInfo.getTitle();

        String content = "你的代码作业" + title + "的代码作业与其他作业高度相似，被判定为抄袭！";

        reminder.setDate(now);
        reminder.setContent(content);
        reminder.setTitle("代码作业警告");
        reminder.setStudentNumber(code.getStudentNumber());
        reminderMapper.insert(reminder);

        Map<String, Object> map = new HashMap<>();
        map.put("提醒", reminder);

        return map;
    }


    public Map<String, Object> generateCpp(int id)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code_info_id", id);

        List<Code> list = codeMapper.selectList(queryWrapper);

        if(list.size() <= 2) map.put("num", 1);

        for(Code code : list)
        {
            String codeContent = code.getContent();
            String student = code.getStudentNumber();
            String cppFilePath = "/root/cpp/" + student + ".cpp";

            try {
                FileWriter writer = new FileWriter(cppFilePath);
                writer.write(codeContent);
                writer.close();

                map.put(student, cppFilePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return map;
    }

    public void deleteFiles()
    {
        String cppFolderPath = "/root/cpp/";

        File folder = new File(cppFolderPath);
        File[] files = folder.listFiles();

        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile())
                {
                    boolean deleted = file.delete();
                    if (deleted)
                    {
                        System.out.println("Deleted file: " + file.getAbsolutePath());
                    }
                    else
                    {
                        System.out.println("Failed to delete file: " + file.getAbsolutePath());
                    }
                }
            }
        }
        else
        {
            System.out.println("No files found in the folder: " + cppFolderPath);
        }
    }

    public void deleteResultFiles()
    {
        String cppFolderPath = "/root/results/";

        File folder = new File(cppFolderPath);
        File[] files = folder.listFiles();

        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile())
                {
                    boolean deleted = file.delete();
                    if (deleted)
                    {
                        System.out.println("Deleted file: " + file.getAbsolutePath());
                    }
                    else
                    {
                        System.out.println("Failed to delete file: " + file.getAbsolutePath());
                    }
                }
            }
        }
        else
        {
            System.out.println("No files found in the folder: " + cppFolderPath);
        }
    }

    public JsonNode runJPlag()
    {
        deleteResultFiles();
        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "java -jar jplag-4.3.0-jar-with-dependencies.jar /root/cpp -l cpp -r /root/results/result -t 5");
            Process process = processBuilder.start();

            // 获取外部进程的输入流
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
            {
                System.out.println(line);
            }

            ProcessBuilder processBuilder2 = new ProcessBuilder("bash", "-c", "unzip /root/results/result.zip");
            processBuilder2.start();

            String path = "/root/overview.json";
            reader = new BufferedReader(new FileReader(path));
            StringBuilder jsonContent = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                jsonContent.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(String.valueOf(jsonContent));
            System.out.println(jsonNode.get("metrics").asText());
            System.out.println(jsonNode.get("metrics").get(0).asText());
            System.out.println(jsonNode.get("metrics").get(0).get("topComparisons"));

            return jsonNode.get("metrics").get(0).get("topComparisons");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    public String runPerl()
    {
        //String command = "perl /root/moss.pl -l cc /root/cpp/*.cpp";
        String url = null;

        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "perl /root/moss.pl -l cc /root/cpp/*.cpp");
            //ProcessBuilder processBuilder = new ProcessBuilder("perl", "/root/moss.pl", "-l", "cc", "/root/cpp/*.cpp");
            //ProcessBuilder processBuilder = new ProcessBuilder("xterm", "-e", "bash", "-c", command);
            Process process = processBuilder.start();

            // 获取外部进程的输入流
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
            {
                System.out.println(line);

                if(line.startsWith("http")) url = line;
            }

            try (InputStream errorStream = process.getErrorStream();
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream)))
            {
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null)
                {
                    System.err.println(errorLine);
                }
            }

            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return url;
    }
}
