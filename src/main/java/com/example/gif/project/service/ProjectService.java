package com.example.gif.project.service;


import com.example.gif.project.dto.ProjectCreateRequest;
import com.example.gif.project.entity.Project;
import com.example.gif.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public Long createProject(String providerId, ProjectCreateRequest request) {

        Project project = new Project(
                request.getProjectName(),
                request.getTeamName(),
                request.getDescription(),
                providerId,
                request.getTeamLogoUrl(),
                request.getMemberProviderIds()
        );

        Project savedProject = projectRepository.save(project);

        return savedProject.getId();
    }

    @Transactional
    public void updateDescription(String providerId,
                                  Long projectId,
                                  String description) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트 없음"));

        if (!project.getLeaderProviderId().equals(providerId)) {
            throw new IllegalArgumentException("수정 권한 없음");
        }

        project.updateDescription(description);
    }

    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}