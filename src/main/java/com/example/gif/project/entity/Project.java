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
    private Long leaderId;

    @Column
    private String teamLogoUrl;

    @ElementCollection
    @CollectionTable(
            name = "project_member",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "user_id")
    private List<Long> memberIds = new ArrayList<>();

    public Project() {}

    public Project(String projectName, String teamName, String description,
                   Long leaderId, String teamLogoUrl, List<Long> memberIds) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.description = description;
        this.leaderId = leaderId;
        this.teamLogoUrl = teamLogoUrl;
        this.memberIds = memberIds;
    }
}