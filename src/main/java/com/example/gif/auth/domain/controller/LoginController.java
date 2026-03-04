package com.example.gif.auth.domain.controller;

import com.example.gif.auth.domain.dto.AdminAdditionalInfo;
import com.example.gif.auth.domain.dto.ClientAdditionalInfo;
import com.example.gif.auth.domain.entity.User;
import com.example.gif.auth.domain.repository.UserRepository;
import com.example.gif.auth.domain.service.OAuth2Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final OAuth2Service service;
    private final UserRepository userRepository;

    @GetMapping("/admin/login")
    public String adminLogin(HttpServletRequest request) {
        request.getSession().setAttribute("loginType", "admin");
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("client/login")
    public String clientLogin(HttpServletRequest request) {
        request.getSession().setAttribute("loginType", "client");
        return "redirect:/oauth2/authorization/google";
    }

    @PostMapping("/client/additional-info")
    public String completeClientInfo(Authentication authentication, @RequestBody ClientAdditionalInfo request) {

        if(authentication == null) {
            throw new IllegalStateException("인증 정보 없음");
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String providerId = oAuth2User.getName();

        User user = userRepository.findByProviderAndProviderId(
                User.Provider.GOOGLE,
                providerId
        ).orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        service.completeClientInfo(user.getId(), request);

        return "client 정보 저장 완료";
    }

    @PostMapping("/admin/additional-info")
    public String completeAdminInfo(Authentication authentication, @RequestBody AdminAdditionalInfo request) {

        if(authentication == null) {
            throw new IllegalStateException("인증 정보 없음");
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String providerId = oAuth2User.getName();

        User user = userRepository.findByProviderAndProviderId(
                User.Provider.GOOGLE,
                providerId
        ).orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        service.completeAdminInfo(user.getId(), request);

        return "admin 정보 저장 완료";
    }
}
