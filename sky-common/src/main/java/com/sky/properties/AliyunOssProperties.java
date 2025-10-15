package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.aliyun.oss")
@Data
public class AliyunOssProperties {
    private String endpoint;
    private String bucketName;
    private String region;
}
