package com.example.gif.auth.domain.entity;

import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(nullable = false, unique = true)
    private String providerId;

    @Column(unique = true)
    private String studentNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Provider {
        GOOGLE
    }

    public enum UserType {
        ADMIN,
        CLIENT
    }

    public enum Role {
        MASTER,
        ADMIN,
        LEADER,
        MEMBER
    }

    public User updateUser(String username, String email) {
        this.username = username;
        this.email = email;
        return this;
    }
}
