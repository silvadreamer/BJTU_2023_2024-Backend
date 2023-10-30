package com.bjtu.backend.service.impl.OSS;

import com.bjtu.backend.config.MyCOSConfig;
import com.bjtu.backend.controller.OSS.OSSUploadController;
import com.bjtu.backend.service.OSS.OSSUploadService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.qcloud.cos.demo.BucketRefererDemo.bucketName;

@Service
public class OSSUploadServiceImpl implements OSSUploadService
{
    final MyCOSConfig myCOSConfig;

    public OSSUploadServiceImpl(MyCOSConfig myCOSConfig)
    {
        this.myCOSConfig = myCOSConfig;
    }

    @Override
    public Map<String, String> upload(MultipartFile multipartFile)
    {
        String fileName = multipartFile.getOriginalFilename();
        String key = myCOSConfig.getFolderPrefix() + fileName;
        return getStringStringMap(multipartFile, fileName, key);
    }

    /**
     *
     * @param prefix 前缀 课程编号_作业编号
     * @param multipartFile 文件
     * @return Map
     */
    @Override
    public Map<String, String> uploadHomework(String prefix, MultipartFile multipartFile)
    {
        String fileName = multipartFile.getOriginalFilename();
        String key = "/" + prefix + "/" + fileName;

        return getStringStringMap(multipartFile, fileName, key);
    }

    /**
     * 上传核心
     * @param multipartFile 文件
     * @param fileName 地址
     * @param key key
     * @return Map
     */
    private Map<String, String> getStringStringMap(MultipartFile multipartFile, String fileName, String key)
    {
        COSClient cosClient = null;

        Map<String, String> map = new HashMap<>();

        File file;
        try
        {
            file = File.createTempFile("temp",null);
            multipartFile.transferTo(file);

            COSCredentials cosCredentials = new BasicCOSCredentials(myCOSConfig.getAccessKey(), myCOSConfig.getSecretKey());
            ClientConfig clientConfig = new ClientConfig(new Region(myCOSConfig.getRegionName()));
            cosClient = new COSClient(cosCredentials, clientConfig);
            PutObjectRequest putObjectRequest = new PutObjectRequest(myCOSConfig.getBucketName(), key, file);
            cosClient.putObject(putObjectRequest);

            map.put("url", myCOSConfig.getBaseUrl()+key);
            map.put("fileName", fileName);
            return map;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (cosClient != null)
            {
                cosClient.shutdown();
            }
        }

        return null;
    }
}
