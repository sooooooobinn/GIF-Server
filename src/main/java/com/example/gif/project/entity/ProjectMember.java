package com.example.gif.project.entity;

import com.example.gif.auth.domain.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User user;

    public ProjectMember(Project project, User user) {
        this.project = project;
        this.user = user;
    }
}
