package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliyunOSSOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * 通用接口
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/common")
public class CommonController {
    private final AliyunOSSOperator aliyunOSSOperator;

    /**
     * 文件上传
     * @param file 文件
     * @return Result
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        log.info("上传文件：{}", file.getOriginalFilename());

        try{
            String url = aliyunOSSOperator.upload(file.getBytes(),
                    Objects.requireNonNull(file.getOriginalFilename()));
            return Result.success(url);
        }catch (Exception e) {
            log.error(e.getMessage());
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
