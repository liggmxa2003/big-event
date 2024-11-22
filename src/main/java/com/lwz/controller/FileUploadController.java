package com.lwz.controller;

import com.lwz.pojo.Result;
import com.lwz.utils.QiNiuOssUtil;
import com.qiniu.common.QiniuException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
public class FileUploadController {

    @Value("${qiniu.access-key}")
    private String accessKey;

    @Value("${qiniu.secret-key}")
    private String secretKey;

    @Value("${qiniu.bucket-name}")
    private String bucketName;

    @Value("${qiniu.domain}")
    private String domain; // 七牛云存储空间的域名

    @PostMapping("/upload")
    public ResponseEntity<Result<String>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Result.error( "文件为空"));
        }

        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // 初始化七牛云工具类
            QiNiuOssUtil.init(accessKey, secretKey, bucketName);

            // 上传文件到七牛云
            String result = QiNiuOssUtil.uploadFile(inputStream, uniqueFileName);

            // 构建图片的 URL
            String imageUrl = domain + "/" + uniqueFileName;

            // 返回统一的响应结果
            return ResponseEntity.ok(Result.success(imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Result.error( "上传失败: " + e.getMessage()));
        }
    }
}
