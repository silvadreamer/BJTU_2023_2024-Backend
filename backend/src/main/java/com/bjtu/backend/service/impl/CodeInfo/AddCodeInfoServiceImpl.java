package com.bjtu.backend.service.impl.CodeInfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.CodeInfoMapper;
import com.bjtu.backend.pojo.CodeInfo;
import com.bjtu.backend.service.CodeInfo.AddCodeInfoService;
import com.mysql.cj.QueryResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddCodeInfoServiceImpl implements AddCodeInfoService
{
    final CodeInfoMapper codeInfoMapper;

    public AddCodeInfoServiceImpl(CodeInfoMapper codeInfoMapper)
    {
        this.codeInfoMapper = codeInfoMapper;
    }

    @Override
    public Map<String, Object> addCodeInfo(int id)
    {
        CodeInfo codeInfo = new CodeInfo();

        String url = "https://www.acwing.com/problem/content/" + id + "/";

        try {
            Document document = Jsoup.connect(url).get();

            Element titleElement = document.selectFirst("div.nice_font.problem-content-title");
            if (titleElement != null)
            {
                String title = titleElement.text().trim();
                int dotIndex = title.indexOf('.');
                title = title.substring(dotIndex + 1);
                codeInfo.setTitle(title);
            }

            Elements elements = document.select("div.ui.bottom.attached.tab.active.martor-preview");

            for (Element element : elements)
            {

                Elements headersAndParagraphs = element.select("h4, p");

                String info = "";
                for (Element item : headersAndParagraphs)
                {

                    String content = item.text();
                    System.out.println(content);

                    if(content.equals("输入格式"))
                    {
                        System.out.println(info);
                        codeInfo.setContent(info);
                        info = "";
                    }
                    else if(content.equals("输出格式"))
                    {
                        System.out.println(info);
                        codeInfo.setInputFormat(info);
                        info = "";
                    }
                    else if(content.equals("数据范围"))
                    {
                        System.out.println(info);
                        codeInfo.setOutputFormat(info);
                        info = "";
                    }
                    else if(content.equals("输入样例：") || content.equals("样例输入："))
                    {
                        System.out.println(info);
                        codeInfo.setNumRange(info);
                        info = "";
                    }
                    else
                    {
                        if(info.equals("")) info += content;
                        else info += "\n" + content;
                    }

                }

            }

            for (Element element : elements)
            {
                Elements paragraphs = element.select("code");
                int index = 0;
                for (Element paragraph : paragraphs)
                {
                    String content = paragraph.text();
                    System.out.println(content);

                    if(index == 0)
                    {
                        codeInfo.setInputCase(content);
                        index ++;
                    }
                    else
                    {
                        codeInfo.setOutputCase((content));
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        codeInfo.setAcwId(id);

        QueryWrapper<CodeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("acw_id", id);
        if(codeInfoMapper.exists(queryWrapper))
        {
            codeInfoMapper.update(codeInfo, queryWrapper);
        }
        else
        {
            codeInfoMapper.insert(codeInfo);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("题面信息", codeInfo);

        return map;
    }
}
