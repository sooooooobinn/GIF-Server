package com.example.gif.auth.global.security;

import com.example.gif.auth.domain.entity.User;
import com.example.gif.auth.domain.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)

        throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        User.Provider provider = User.Provider.GOOGLE;
        String providerId = oAuth2User.getName();

        String loginType =
                (String) request.getSession().getAttribute("loginType");

        if(loginType == null) {
            response.sendRedirect("/login/error");
            return;
        }

        User.UserType userType =
                "admin".equalsIgnoreCase(loginType)
                        ? User.UserType.ADMIN
                        : User.UserType.CLIENT;

        userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() ->
                        userRepository.save(
                                User.builder()
                                        .username(name)
                                        .email(email)
                                        .provider(provider)
                                        .providerId(providerId)
                                        .userType(userType)
                                        .role(null)
                                        .build()
                        )
                );

        request.getSession().removeAttribute("loginType");

        response.sendRedirect("/oauth/loginInfo");
    }
}
