package com.example.gif.auth.domain.dto;

import com.example.gif.auth.domain.entity.User;
import lombok.Getter;

@Getter
public class ClientAdditionalInfo {
    private String username;
    private String studentNumber;
    private User.Role role;
}
