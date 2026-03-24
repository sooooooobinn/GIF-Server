package com.example.gif.entity;

import jakarta.persistence.*;

@Entity
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;
    private Long userId;

    public ProjectMember() {}

    public ProjectMember(Long projectId, Long userId) {
        this.projectId = projectId;
        this.userId = userId;
    }
}