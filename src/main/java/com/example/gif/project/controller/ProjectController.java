package com.example.gif.project.controller;

import com.example.gif.project.dto.ProjectCreateRequest;
import com.example.gif.project.dto.ProjectUpdateDescriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.gif.project.service.ProjectService;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

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
        Long projectId = projectService.createProject(providerId, request);

        URI location = URI.create("/project/" + projectId);

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{projectId}/description")
    public ResponseEntity<?> updateDescription(
            @PathVariable Long projectId,
            @AuthenticationPrincipal String providerId,
            @RequestBody ProjectUpdateDescriptionRequest request
    ) {
        projectService.updateDescription(
                providerId,
                projectId,
                request.getDescription()
        );

        return ResponseEntity.ok("소개글 수정 완료");
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllProjects(
            @AuthenticationPrincipal String providerId
    ) {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getProjects(
            @RequestParam(required = false) Integer grade
    ) {
        if (grade != null) {
            return ResponseEntity.ok(projectService.getProjectsByGrade(grade));
        }
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping("/{projectId}/ppt")
    public ResponseEntity<?> uploadPpt(
            @PathVariable Long projectId,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal String providerId
    ) {
        projectService.uploadPpt(projectId, providerId, file);
        return ResponseEntity.ok("PPT 제출 완료");
    }

    @PatchMapping("/{projectId}/logo")
    public ResponseEntity<?> updateLogo(
            @PathVariable Long projectId,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal String providerId
    ) {
        projectService.updateLogo(projectId, providerId, file);
        return ResponseEntity.ok("로고 수정 완료");
    }
}   