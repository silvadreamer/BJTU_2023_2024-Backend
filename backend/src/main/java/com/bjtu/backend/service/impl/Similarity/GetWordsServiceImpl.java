package com.bjtu.backend.service.impl.Similarity;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.HomeworkStudentMapper;
import com.bjtu.backend.mapper.SimilarityMapper;
import com.bjtu.backend.pojo.HomeworkStudent;
import com.bjtu.backend.pojo.Similarity;
import com.bjtu.backend.service.Similarity.GetWordsService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.EmptyFileException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class GetWordsServiceImpl implements GetWordsService
{
    final HomeworkStudentMapper homeworkStudentMapper;
    final SimilarityMapper similarityMapper;

    public GetWordsServiceImpl(HomeworkStudentMapper homeworkStudentMapper, SimilarityMapper similarityMapper)
    {
        this.homeworkStudentMapper = homeworkStudentMapper;
        this.similarityMapper = similarityMapper;
    }


    public String getDocWord(String path, int index)
    {
        String text = "";
        try
        {
            FileInputStream fis = new FileInputStream(path);

            XWPFDocument document = new XWPFDocument(fis);

            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            text = extractor.getText();

            fis.close();
        }
        catch (IOException | EmptyFileException e)
        {
            if(index == 0) getDocWord(path, 1);
            e.printStackTrace();
        }

        return text;
    }

    public String getPdfWord(String path, int index)
    {
        String text = "";
        try
        {
            File file = new File(path);

            PDDocument document = PDDocument.load(file);

            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            text = pdfTextStripper.getText(document);

            document.close();
        }
        catch (IOException | EmptyFileException e)
        {
            if(index == 0) getPdfWord(path, 1);
            e.printStackTrace();
        }

        return text;
    }

    public String getTxtWord(String path, int index)
    {
        String text = "";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            String line;

            while ((line = reader.readLine()) != null)
            {
                text += line + "\n";
            }


            reader.close();
        }
        catch (IOException | EmptyFileException e)
        {
            if(index ==0 ) getTxtWord(path, 1);
            e.printStackTrace();
        }

        return text;
    }

    @Override
    public Map<String, Object> getContent(int classId, int homeworkId)
    {
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classId).eq("homework_id", homeworkId);

        List<HomeworkStudent> list = homeworkStudentMapper.selectList(queryWrapper);

        Map<String, Object> map = new HashMap<>();

        for(HomeworkStudent homeworkStudent : list)
        {
            String studentNumber = homeworkStudent.getStudentNumber();

            map.put(studentNumber, downloadToServer(studentNumber, classId, homeworkId));
        }

        return map;
    }

    public Map<String, Object> downloadToServer(String studentNumber, int classId, int homeworkId)
    {
        //先获得所有文件
        QueryWrapper<HomeworkStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id", homeworkId).eq("class_id", classId).eq("student_number", studentNumber);

        HomeworkStudent homeworkStudent = homeworkStudentMapper.selectOne(queryWrapper);

        String fileNames = homeworkStudent.getFileName();
        String content = homeworkStudent.getAnswer();

        if(fileNames != null)
        {
            String[] parts = fileNames.split("\\|");

            Set<String> stringSet = new HashSet<>();
            stringSet.addAll(Arrays.asList(parts));

            String path = classId + "/" + homeworkId + "/" + studentNumber + "/";
            //下载到本地
            for (String element : stringSet)
            {

                Path filePath = Paths.get(path + element);
                boolean fileExists = Files.exists(filePath);
                File file = new File(path + element);
                if(fileExists && file.length() != 0) continue;


                if(element.endsWith(".docx") || element.endsWith(".doc") || element.endsWith(".pdf") || element.endsWith(".txt"))
                {
                    System.out.println(element);
                    String newPath = path + element;
                    ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "coscmd download " + newPath + " ~/" + path);
                    try
                    {
                        processBuilder.start();
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }

            for(String element : stringSet)
            {
                if(element.endsWith(".docx") || element.endsWith(".doc"))
                {
                    String text = getDocWord("/root/" + path + element, 0);
                    content += "." + text;
                }

                if(element.endsWith(".pdf"))
                {
                    String text = getPdfWord("/root/" + path + element, 0);
                    content += "." + text;
                }

                if(element.endsWith(".txt"))
                {
                    String text = getTxtWord("/root/" + path + element, 0);
                    content += "." + text;
                }
            }

            QueryWrapper<Similarity> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("class_id", classId).eq("homework_id", homeworkId)
                    .eq("student_number", studentNumber);

            if(similarityMapper.exists(queryWrapper1))
            {
                Similarity similarity = similarityMapper.selectOne(queryWrapper1);
                similarity.setContent(content);
                similarityMapper.update(similarity, queryWrapper1);
            }
            else
            {
                Similarity similarity = new Similarity();
                similarity.setHomeworkId(homeworkId);
                similarity.setClassId(classId);
                similarity.setStudentNumber(studentNumber);
                similarity.setContent(content);
                similarityMapper.insert(similarity);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("info", content);
        return map;
    }

}
