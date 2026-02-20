package com.example.gif.auth.domain.dto;

import com.example.gif.auth.domain.entity.User;
import lombok.Getter;

@Getter
public class ClientAdditionalInfo {
    private String username;
    private String studentNumber;
    private User.Role role;

    public void setUsername(String username) {
        this.username = username;
    }
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    public void setRole(User.Role role) {
        this.role = role;
    }
}
