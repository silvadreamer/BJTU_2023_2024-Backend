package com.bjtu.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.ali")
public class MyAliConfig
{
    private String accessKey;
    private String secretKey;
}
