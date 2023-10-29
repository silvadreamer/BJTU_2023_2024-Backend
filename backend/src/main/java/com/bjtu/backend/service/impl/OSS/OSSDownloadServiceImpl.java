package com.bjtu.backend.service.impl.OSS;

import com.bjtu.backend.config.MyCOSConfig;
import com.bjtu.backend.service.OSS.OSSDeleteService;
import com.bjtu.backend.service.OSS.OSSDownloadService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class OSSDownloadServiceImpl implements OSSDownloadService
{
    final MyCOSConfig myCOSConfig;

    public OSSDownloadServiceImpl(MyCOSConfig myCOSConfig)
    {
        this.myCOSConfig = myCOSConfig;
    }

    @Override
    public Map<String, Object> download(String fileName)
    {
        String key = myCOSConfig.getFolderPrefix() + fileName;

        return getStringObjectMap(key);
    }


    @Override
    public Map<String, Object> downloadHomework(String prefix, String fileName)
    {
        String key = "/" + prefix + "/" + fileName;

        return getStringObjectMap(key);
    }

    private Map<String, Object> getStringObjectMap(String key)
    {
        Map<String, Object> map = new HashMap<>();

        COSClient cosclient;
        try
        {
            COSCredentials cosCredentials = new BasicCOSCredentials(myCOSConfig.getAccessKey(), myCOSConfig.getSecretKey());
            ClientConfig clientConfig = new ClientConfig(new Region(myCOSConfig.getRegionName()));
            cosclient = new COSClient(cosCredentials, clientConfig);

            Date expirationDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000);

            URL url = cosclient.generatePresignedUrl(myCOSConfig.getBucketName(), key, expirationDate);

            map.put("url", url);

            cosclient.shutdown();

            return map;
        }
        catch (CosClientException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
