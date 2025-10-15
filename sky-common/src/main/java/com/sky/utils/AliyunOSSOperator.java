package com.sky.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.sky.properties.AliyunOssProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@Slf4j
@Component
@RequiredArgsConstructor
public class AliyunOSSOperator {
    // 阿里云配置信息
    private final AliyunOssProperties aliyunOSSProperties;

    public String upload(byte[] data, String originalFileName) throws Exception {
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String region = aliyunOSSProperties.getRegion();

        // 获取当前系统日期的字符串,格式为 yyyy/MM
        LocalDate currentDate =  LocalDate.now();
        String dir = currentDate.format(DateTimeFormatter.ofPattern("yyyy/MM"));

        // 使用UUID生成一个新的不重复的文件名
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid + originalFileName.substring(originalFileName.
                lastIndexOf("."));

        String objectName = dir + "/" + newFileName;

        // 从环境变量中获取访问凭证
        EnvironmentVariableCredentialsProvider credentialsProvider =
                CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 创建OSSClient实例。
        // 当OSSClient实例不再使用时，调用shutdown方法以释放资源。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        // 简单上传文件
        try{
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(data));
        }finally {
            ossClient.shutdown();
        }

        // 返回url
        return endpoint.split("//")[0] + "//" + bucketName + "." +
                endpoint.split("//")[1] + "/" + objectName;
    }
}
