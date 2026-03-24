package com.example.gif.controller;

import com.example.gif.dto.ProjectCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.gif.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectCreateRequest request) {
        projectService.createProject(request);
        return ResponseEntity.ok().build();
    }
}
