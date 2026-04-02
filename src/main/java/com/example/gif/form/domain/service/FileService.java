package com.example.gif.form.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = "uploads/";

    public String upload(MultipartFile file) {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);

        dest.getParentFile().mkdirs();

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패");
        }

        return "/uploads/" + fileName;
    }
}