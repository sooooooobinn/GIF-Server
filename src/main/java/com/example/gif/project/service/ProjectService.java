package com.example.gif.project.service;


import com.example.gif.project.dto.ProjectCreateRequest;
import com.example.gif.project.entity.Project;
import com.example.gif.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public void createProject(String providerId, ProjectCreateRequest request) {

        Project project = new Project(
                request.getProjectName(),
                request.getTeamName(),
                request.getDescription(),
                providerId,
                request.getTeamLogoUrl(),
                request.getMemberProviderIds()
        );

        projectRepository.save(project);
    }
}