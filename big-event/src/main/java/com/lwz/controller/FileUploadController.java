package com.lwz.controller;

import com.lwz.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    // 模拟文件上传
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        // 文件内容存储到本地磁盘
        String originalFilename = file.getOriginalFilename();
        //确保文件名唯一,防着文件覆盖
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        file.transferTo(new File("C:\\Users\\LiGG\\Desktop\\upload\\"+filename));
        return Result.success(filename);
    }
}
