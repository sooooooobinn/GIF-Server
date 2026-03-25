package com.example.gif.project.controller;

import com.example.gif.project.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/file-upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file

    ) {
        String url = fileService.upload(file);
        return ResponseEntity.ok(url);
    }
}