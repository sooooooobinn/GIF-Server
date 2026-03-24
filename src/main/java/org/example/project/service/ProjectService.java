package org.example.project.service;


import org.example.project.dto.ProjectCreateRequest;
import org.example.project.entity.Project;
import org.example.project.entity.ProjectMember;
import org.example.project.entity.Role;
import org.example.project.entity.User;
import org.example.project.repository.ProjectMemberRepository;
import org.example.project.repository.ProjectRepository;
import org.example.project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository,
                          ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Transactional
    public void createProject(ProjectCreateRequest request) {

        Long loginUserId = 1L;

        User leader = userRepository.findById(loginUserId)
                .orElseThrow();

        if (leader.getRole() != Role.TEAM_LEADER) {
            throw new RuntimeException("팀장만 프로젝트 생성 가능");
        }

        Project project = new Project(
                request.getProjectName(),
                request.getTeamName(),
                request.getDescription(),
                leader.getId()
        );

        projectRepository.save(project);

        for (String studentNumber : request.getMembers()) {

            User member = userRepository
                    .findByStudentNumber(studentNumber)
                    .orElseThrow();

            if (member.getRole() != Role.MEMBER) {
                throw new RuntimeException("팀원만 추가 가능");
            }

            ProjectMember projectMember =
                    new ProjectMember(project.getId(), member.getId());

            projectMemberRepository.save(projectMember);
        }
    }
}