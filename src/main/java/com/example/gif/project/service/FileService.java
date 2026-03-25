package com.example.gif.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileService {

    private final String uploadDir = "C:/upload/"; // 원하는 경로

    public String upload(MultipartFile file) {

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            // 나중에 접근할 URL
            return "/images/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패");
        }
    }
}
