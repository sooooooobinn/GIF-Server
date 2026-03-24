package org.example.project.controller;

import org.example.project.dto.ProjectCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.project.service.ProjectService;

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
