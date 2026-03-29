package com.example.gif.project.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;
    private String teamName;

    private String description;

    private String leaderProviderId;
    private String teamLogoUrl;
    private String pptUrl;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMember> members = new ArrayList<>();

    @Column(nullable = false)
    private Integer grade;

    protected Project() {}

    public Project(String projectName, String teamName, String description,
                   String leaderProviderId, String teamLogoUrl,
                   Integer grade, String pptUrl) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.description = description;
        this.leaderProviderId = leaderProviderId;
        this.teamLogoUrl = teamLogoUrl;
        this.grade = grade;
        this.pptUrl = pptUrl;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updatePptUrl(String pptUrl) {
        this.pptUrl = pptUrl;
    }

    public void updateTeamLogoUrl(String teamLogoUrl) {
        this.teamLogoUrl = teamLogoUrl;
    }

}