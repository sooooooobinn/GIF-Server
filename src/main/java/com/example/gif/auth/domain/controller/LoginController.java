package com.example.gif.auth.domain.controller;

import com.example.gif.auth.domain.dto.AdminAdditionalInfo;
import com.example.gif.auth.domain.dto.ClientAdditionalInfo;
import com.example.gif.auth.domain.service.OAuth2Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final OAuth2Service service;

    @GetMapping("/admin/login")
    public String adminLogin(HttpServletRequest request) {
        request.getSession().setAttribute("loginType", "admin");
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/client/login")
    public String clientLogin(HttpServletRequest request) {
        request.getSession().setAttribute("loginType", "client");
        return "redirect:/oauth2/authorization/google";
    }

    @PostMapping("/client/additional-info")
    public String completeClientInfo(Authentication authentication, @RequestBody ClientAdditionalInfo request) {
        String providerId = authentication.getName(); // JWT 필터에서 가져온 ID
        service.completeClientInfo(providerId, request);
        return "client 정보 저장 완료";
    }

    @PostMapping("/admin/additional-info")
    public String completeAdminInfo(Authentication authentication, @RequestBody AdminAdditionalInfo request) {
        String providerId = authentication.getName();
        service.completeAdminInfo(providerId, request);
        return "admin 정보 저장 완료";
    }
}