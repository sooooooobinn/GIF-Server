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

    @Column(length = 2000)
    private String description;

    private String leaderProviderId;
    private String teamLogoUrl;

    @ElementCollection
    @CollectionTable(
            name = "project_member",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "provider_id")
    private List<String> memberProviderIds = new ArrayList<>();

    protected Project() {}

    public Project(String projectName,
                   String teamName,
                   String description,
                   String leaderProviderId,
                   String teamLogoUrl,
                   List<String> memberProviderIds) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.description = description;
        this.leaderProviderId = leaderProviderId;
        this.teamLogoUrl = teamLogoUrl;
        this.memberProviderIds = memberProviderIds;
    }

}