package com.bjtu.backend.service.impl.Code;

import cn.hutool.aop.interceptor.SpringCglibInterceptor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.CodeMapper;
import com.bjtu.backend.mapper.SubmissionMapper;
import com.bjtu.backend.pojo.Code;
import com.bjtu.backend.service.Code.CodeCheckService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jplag.JPlag;
import de.jplag.JPlagResult;
import de.jplag.Language;
import de.jplag.Submission;
import de.jplag.exceptions.ExitException;
import de.jplag.options.JPlagOptions;
import de.jplag.reporting.reportobject.ReportObjectFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

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

    @Override
    public Map<String, Object> JPlagCheck(int id)
    {
        Map<String, Object> map = new HashMap<>();

        deleteFiles();
        map.put("info", generateCpp(id));
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


    public Map<String, Object> generateCpp(int id)
    {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code_info_id", id);

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

    public JsonNode runJPlag()
    {
//        Language language = new de.jplag.JPlag(new JPlagOptions(Language()));
//        Language language = new de.jplag.java.Language();
//        Set<File> submissionDirectories = new HashSet<>();
//        submissionDirectories.add(new File("/root/cpp"));
//        //File baseCode = new File("/path/to/baseCode");
//        Set<File> files = new HashSet<>();
//        JPlagOptions options = new JPlagOptions(language, submissionDirectories, files);
//
//        JPlag jplag = new JPlag(options);
//        try {
//            JPlagResult result = jplag.run();
//
//            // Optional
//            ReportObjectFactory reportObjectFactory = new ReportObjectFactory();
//            reportObjectFactory.createAndSaveReport(result, "/root/output");
//        } catch (ExitException e) {
//            // error handling here
//            return "请联系管理员";
//        }

        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "java -jar jplag-4.3.0-jar-with-dependencies.jar /root/cpp -l cpp -r /root/result -t 5");
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
            }

//            try (InputStream errorStream = process.getErrorStream();
//                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream)))
//            {
//                String errorLine;
//                while ((errorLine = errorReader.readLine()) != null)
//                {
//                    System.err.println(errorLine);
//                }
//            }
//
//            int exitCode = process.waitFor();
//            System.out.println("Exit Code: " + exitCode);

            ProcessBuilder processBuilder2 = new ProcessBuilder("bash", "-c", "unzip result.zip");
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
