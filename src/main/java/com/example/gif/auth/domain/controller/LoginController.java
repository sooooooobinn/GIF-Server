package com.example.gif.auth.domain.controller;

import com.example.gif.auth.domain.dto.AdminAdditionalInfo;
import com.example.gif.auth.domain.dto.ClientAdditionalInfo;
import com.example.gif.auth.domain.entity.User;
import com.example.gif.auth.domain.service.OAuth2Service;
import com.example.gif.auth.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final OAuth2Service service;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${oauth2.authorization.google-uri}")
    private String googleLoginBaseUri;

    @GetMapping("/admin/login")
    public void adminLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(googleLoginBaseUri + "?loginType=admin");
    }

    @GetMapping("/client/login")
    public void clientLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(googleLoginBaseUri + "?loginType=client");
    }

    @PostMapping("/client/additional-info")
    public ResponseEntity<Map<String, Object>> completeClientInfo(Authentication authentication, @RequestBody ClientAdditionalInfo request) {
        String providerId = authentication.getName();
        User user = service.completeClientInfo(providerId, request);

        String roleName = "ROLE_" + user.getRole().name();
        String newToken = jwtTokenProvider.createToken(user.getProviderId(), user.getEmail(), roleName);

        return ResponseEntity.ok(Map.of(
                "message", "client 정보 저장",
                "accessToken", newToken
        ));
    }

    @PostMapping("/admin/additional-info")
    public ResponseEntity<Map<String, Object>> completeAdminInfo(Authentication authentication, @RequestBody AdminAdditionalInfo request) {
        String providerId = authentication.getName();
        User user = service.completeAdminInfo(providerId, request);

        String newToken = jwtTokenProvider.createToken(user.getProviderId(), user.getEmail(), "ROLE_ADMIN");

        return ResponseEntity.ok(Map.of(
                "message", "admin 정보 저장",
                "accessToken", newToken
        ));
    }
}