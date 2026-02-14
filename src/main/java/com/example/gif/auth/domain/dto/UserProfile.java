package com.example.gif.auth.domain.dto;

import com.example.gif.auth.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfile {

    private String username;

    private User.Provider provider;

    private String email;

    private String providerId;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .provider(this.provider)
                .providerId(this.providerId)
                .build();
    }
}