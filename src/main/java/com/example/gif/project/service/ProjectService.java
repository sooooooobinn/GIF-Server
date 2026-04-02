package com.example.gif.project.service;


import com.example.gif.auth.domain.entity.User;
import com.example.gif.auth.domain.repository.UserRepository;
import com.example.gif.project.dto.ProjectCreateRequest;
import com.example.gif.project.entity.Project;
import com.example.gif.project.entity.ProjectMember;
import com.example.gif.project.repository.ProjectMemberRepository;
import com.example.gif.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final FileProjectService fileService;

    @Transactional
    public Long createProject(String providerId, ProjectCreateRequest request) {

        Project project = new Project(
                request.getProjectName(),
                request.getTeamName(),
                request.getDescription(),
                providerId,
                request.getTeamLogoUrl(),
                request.getGrade(),
                null
        );

        Project savedProject = projectRepository.save(project);


        if (request.getMemberProviderIds() != null) {
            for (String memberProviderId : request.getMemberProviderIds()) {

                User user = userRepository
                        .findByProviderAndProviderId(User.Provider.GOOGLE, memberProviderId)
                        .orElseThrow(() -> new RuntimeException("유저 없음"));

                projectMemberRepository.save(new ProjectMember(savedProject, user));
            }
        }

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

    @Transactional(readOnly = true)
    public List<Project> getProjectsByGrade(Integer grade) {
        return projectRepository.findByGrade(grade);
    }

    @Transactional
    public void uploadPpt(Long projectId, String providerId, MultipartFile file) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("프로젝트 없음"));

        if (!project.getLeaderProviderId().equals(providerId)) {
            throw new RuntimeException("팀장만 제출 가능");
        }

        String fileUrl = fileService.uploadFile(file);

        project.updatePptUrl(fileUrl);
    }

    @Transactional
    public void updateLogo(Long projectId, String providerId, MultipartFile file) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("프로젝트 없음"));

        if (!project.getLeaderProviderId().equals(providerId)) {
            throw new RuntimeException("팀장만 수정 가능");
        }

        String fileUrl = fileService.uploadFile(file);

        project.updateTeamLogoUrl(fileUrl);
    }

}