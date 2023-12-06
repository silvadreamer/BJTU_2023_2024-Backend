package com.bjtu.backend.service.impl.Similarity;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alinlp.model.v20200629.GetTsChEcomRequest;
import com.aliyuncs.alinlp.model.v20200629.GetTsChEcomResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.backend.config.MyAliConfig;
import com.bjtu.backend.mapper.SimilarityMapper;
import com.bjtu.backend.pojo.Similarity;
import com.bjtu.backend.service.Similarity.WordsSimilarityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordSimilarityServiceImpl implements WordsSimilarityService
{
    final MyAliConfig myAliConfig;
    final SimilarityMapper similarityMapper;

    public WordSimilarityServiceImpl(MyAliConfig myAliConfig, SimilarityMapper similarityMapper)
    {
        this.myAliConfig = myAliConfig;
        this.similarityMapper = similarityMapper;
    }

    private Map<String, String> apiForSimilarity(String q, String t) throws ClientException
    {
        Map<String, String> map = new HashMap<>();

        String accessKeyId = myAliConfig.getAccessKey();
        String accessKeySecret = myAliConfig.getSecretKey();
        DefaultProfile defaultProfile = DefaultProfile.getProfile(
                "cn-hangzhou",
                accessKeyId,
                accessKeySecret);
        IAcsClient client = new DefaultAcsClient(defaultProfile);
        //构造请求参数，其中GetTsChEcom是算法的actionName
        GetTsChEcomRequest request = new GetTsChEcomRequest();
        //固定值，无需更改
        request.setSysEndpoint("alinlp.cn-hangzhou.aliyuncs.com");
        //固定值，无需更改
        request.setServiceCode("alinlp");

        request.setType("similarity");

        System.out.println("q:"  + q);
        System.out.println("t:" + t);

        request.setOriginQ(q);
        request.setOriginT(t);

        long start = System.currentTimeMillis();
        //获取请求结果，注意这里的GetTsChEcom也需要替换
        GetTsChEcomResponse response = client.getAcsResponse(request);

        map.put("data", response.getData());

        System.out.println(response.hashCode());
        System.out.println(response.getRequestId() + "\n" + response.getData() + "\n" + "cost:" + (System.currentTimeMillis()- start));

        return map;
    }

    @Override
    public Map<String, Object> checkSimilarity(int classId, int homeworkId)
    {
        Map<String, Object> result = new HashMap<>();
        int num = 0;

        QueryWrapper<Similarity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classId).eq("homework_id", homeworkId);

        List<Similarity> list = similarityMapper.selectList(queryWrapper);

        int length = list.size();

        for(int i = 0; i < length; i ++)
        {
            Similarity similarity1 = list.get(i);
            String student1 = similarity1.getStudentNumber();
            String content1 = similarity1.getContent();
            content1 = content1.replaceAll("\\s+", " ");
            if(content1.length() >= 500)
            {
                content1 = content1.substring(0, 499);
            }

            for(int j = i + 1; j < length; j ++)
            {
                Similarity similarity2 = list.get(j);
                String student2 = similarity2.getStudentNumber();
                String content2 = similarity2.getContent();
                content2 = content2.replaceAll("\\s+", " ");
                if(content2.length() >= 500)
                {
                    content2 = content2.substring(0, 499);
                }

                try
                {
                    var map = apiForSimilarity(content1, content2);
                    String jsonString = map.get("data");

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(jsonString);

                    JsonNode scoreNode = jsonNode.get("result").get(0).get("score");
                    double score = Double.parseDouble(scoreNode.asText());

                    if(score < 0.5) continue;

                    Map<String, Double> map2 = new HashMap<>();
                    map2.put(student1, score);
                    map2.put(student2, score);
                    result.put(String.valueOf(num), map2);
                    num ++;
                }
                catch (ClientException | JsonProcessingException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }

        return result;
    }
}
