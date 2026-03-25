package com.example.gif.project.controller;

import com.example.gif.project.dto.ProjectCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.gif.project.service.ProjectService;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(
            @AuthenticationPrincipal String providerId,
            @RequestBody ProjectCreateRequest request
    ) {
        projectService.createProject(providerId, request);
        return ResponseEntity.ok("프로젝트 생성 완료");
    }
}