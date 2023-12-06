package com.bjtu.backend.service.impl.Similarity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.mapper.SimilarityMapper;
import com.bjtu.backend.pojo.Similarity;
import com.bjtu.backend.service.Similarity.JiebaSimilarityService;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JiebaSimilarityServiceImpl implements JiebaSimilarityService
{
    final SimilarityMapper similarityMapper;

    public JiebaSimilarityServiceImpl(SimilarityMapper similarityMapper)
    {
        this.similarityMapper = similarityMapper;
    }

    @Override
    public Map<String, Object> jiebaSimilarity(int classId, int homeworkId)
    {
        Map<String, Object> map_ = new HashMap<>();
        int index = 0;
        QueryWrapper<Similarity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classId).eq("homework_id", homeworkId);

        List<Similarity> list = similarityMapper.selectList(queryWrapper);

        int total = 0;
        int length = list.size();

        for(int i = 0; i < length; i ++)
        {
            Similarity similarity1 = list.get(i);
            String student1 = similarity1.getStudentNumber();
            String content1 = similarity1.getContent();
            content1 = content1.replaceAll("\\s+", " ");
            JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();

            List<String> result = jiebaSegmenter.sentenceProcess(content1);

            for (int j = i + 1; j < length; j++)
            {
                Similarity similarity2 = list.get(j);
                String student2 = similarity2.getStudentNumber();
                String content2 = similarity2.getContent();
                content2 = content2.replaceAll("\\s+", " ");
                List<String> result2 = jiebaSegmenter.sentenceProcess(content2);
                Map<String, Integer> map = new HashMap<>();

                for (String text1 : result)
                {
                    if (!map.containsKey(text1))
                    {
                        map.put(text1, index);
                        index++;
                    }
                }

                System.out.println(map.size());

                for (String text : result2)
                {
                    if (!map.containsKey(text))
                    {
                        map.put(text, index);
                        index++;
                    }
                }

                Map<String, Integer> result_map = new HashMap<>();
                for (String text : result)
                {
                    if (result_map.containsKey(text))
                    {
                        result_map.replace(text, result_map.get(text) + 1);
                    }
                    else
                    {
                        result_map.put(text, 1);
                    }
                }

                Map<String, Integer> result_map1 = new HashMap<>();
                for (String text : result2)
                {
                    if (result_map1.containsKey(text))
                    {
                        result_map1.replace(text, result_map1.get(text) + 1);
                    }
                    else
                    {
                        result_map1.put(text, 1);
                    }
                }

                ArrayList<Integer> arrayList1 = new ArrayList<>();
                ArrayList<Integer> arrayList2 = new ArrayList<>();

                for (String key : map.keySet())
                {
                    if (result_map.containsKey(key))
                    {
                        Integer num1 = result_map.get(key);
                        arrayList1.add(num1);
                    }
                    else
                    {
                        arrayList1.add(0);
                    }

                    if (result_map1.containsKey(key))
                    {
                        Integer num2 = result_map1.get(key);
                        arrayList2.add(num2);
                    }
                    else
                    {
                        arrayList2.add(0);
                    }
                }

                //计算
                double x_pow = 0, y_pow = 0, sum = 0;

                for (int k = 0; k < arrayList1.size(); k++)
                {
                    x_pow += Math.pow(arrayList1.get(k), 2);
                    y_pow += Math.pow(arrayList2.get(k), 2);
                    sum += arrayList1.get(k) * arrayList2.get(k);
                }

                double cos = sum / (Math.pow(x_pow, 0.5) * Math.pow(y_pow, 0.5));

                if (cos > 0.5)
                {
                    Map<String, Double> map_cos = new HashMap<>();
                    map_cos.put(student1, cos);
                    map_cos.put(student2, cos);
                    map_.put(String.valueOf(total), map_cos);
                    total ++;
                }
            }
        }
        return map_;
    }
}
