package com.example.gif.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileProjectService {

    @Value("${spring.file.upload-dir}")
    private String uploadDir;

    public String upload(MultipartFile file) {

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            return "/upload/" + fileName;

        } catch (java.io.IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    public String uploadFile(MultipartFile file) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String uuid = UUID.randomUUID().toString();
            String originalName = file.getOriginalFilename();
            String savedName = uuid + "_" + originalName;

            File dest = new File(uploadDir + savedName);
            file.transferTo(dest);
            return "/uploads/" + savedName;

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }
}
