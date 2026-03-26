package com.example.gif.auth.global.security;

import com.example.gif.auth.domain.entity.User;
import com.example.gif.auth.domain.repository.UserRepository;
import com.example.gif.auth.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String providerId = oAuth2User.getName();

        User user = userRepository.findByProviderAndProviderId(User.Provider.GOOGLE, providerId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        String role = user.getRole() != null ? "ROLE_" + user.getRole().name() : "ROLE_GUEST";

        String accessToken = jwtTokenProvider.createToken(
                user.getProviderId(),
                user.getEmail(),
                role
        );

        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth/callback")
                .queryParam("accessToken", accessToken)
                .build().toUriString();

        response.sendRedirect(targetUrl);
    }
}