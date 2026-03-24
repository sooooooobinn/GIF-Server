package com.example.gif.common.auth;

import com.example.gif.domain.form.entity.User;

// 임시 Mock 인증 유저입니다
// Oauth 개발 완료시 삭제하기
public class MockAuthUser {

    public static User getAdminUser() {
        return User.builder()
                .id(1L)
                .email("admin@gif.com")
                .name("관리자")
                .role(User.Role.ADMIN)
                .build();
    }

    public static User getNormalUser() {
        return User.builder()
                .id(2L)
                .email("user@gif.com")
                .name("학생")
                .role(User.Role.USER)
                .build();
    }
}
