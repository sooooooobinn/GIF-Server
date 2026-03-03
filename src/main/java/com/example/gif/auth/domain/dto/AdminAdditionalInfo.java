package com.example.gif.auth.domain.dto;

import com.example.gif.auth.domain.entity.User;
import lombok.Getter;

@Getter
public class AdminAdditionalInfo {
    private String username;
    private User.Role role;
    private boolean master;
}
