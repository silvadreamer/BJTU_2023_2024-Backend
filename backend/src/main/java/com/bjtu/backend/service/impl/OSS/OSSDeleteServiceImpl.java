package com.bjtu.backend.service.impl.OSS;

import com.bjtu.backend.config.MyCOSConfig;
import com.bjtu.backend.service.OSS.OSSDeleteService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Service;

@Service
public class OSSDeleteServiceImpl implements OSSDeleteService
{
    final MyCOSConfig myCOSConfig;

    public OSSDeleteServiceImpl(MyCOSConfig myCOSConfig)
    {
        this.myCOSConfig = myCOSConfig;
    }

    @Override
    public boolean delete(String fileName)
    {
        String key = myCOSConfig.getFolderPrefix() + fileName;

        COSClient cosclient;
        try
        {
            COSCredentials cosCredentials = new BasicCOSCredentials(myCOSConfig.getAccessKey(), myCOSConfig.getSecretKey());
            ClientConfig clientConfig = new ClientConfig(new Region(myCOSConfig.getRegionName()));
            cosclient = new COSClient(cosCredentials, clientConfig);
            cosclient.deleteObject(myCOSConfig.getBucketName(), key);

            cosclient.shutdown();

            return true;
        }
        catch (CosClientException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
