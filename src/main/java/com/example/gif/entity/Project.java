package com.example.gif.entity;

import jakarta.persistence.*;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;
    private String teamName;
    private String description;
    private Long leaderId;

    public Project() {}

    public Project(String projectName, String teamName, String description, Long leaderId) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.description = description;
        this.leaderId = leaderId;
    }

    public Long getId() {
        return id;
    }
}