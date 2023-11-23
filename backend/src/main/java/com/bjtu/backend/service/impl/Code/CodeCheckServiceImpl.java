package com.bjtu.backend.service.impl.Code;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.CodeMapper;
import com.bjtu.backend.pojo.Code;
import com.bjtu.backend.service.Code.CodeCheckService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Service
public class CodeCheckServiceImpl implements CodeCheckService
{
    final CodeMapper codeMapper;

    public CodeCheckServiceImpl(CodeMapper codeMapper)
    {
        this.codeMapper = codeMapper;
    }

    @Override
    public Map<String, Object> check(int id)
    {

        Map<String, Object> map = new HashMap<>();

        deleteFiles();
        map.put("info", generateCpp(id));
        String url = runPerl();
        map.put("url", url);

        return map;
    }


    public Map<String, Object> generateCpp(int id)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code_homework_id", id);

        List<Code> list = codeMapper.selectList(queryWrapper);

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
