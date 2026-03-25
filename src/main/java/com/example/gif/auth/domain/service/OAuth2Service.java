package com.example.gif.auth.domain.service;

import com.example.gif.auth.domain.dto.AdminAdditionalInfo;
import com.example.gif.auth.domain.dto.ClientAdditionalInfo;
import com.example.gif.auth.domain.dto.UserProfile;
import com.example.gif.auth.domain.entity.User;
import com.example.gif.auth.domain.repository.UserRepository;
import com.example.gif.auth.global.security.oauth.OAuthAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Object loginTypeObj = userRequest.getAdditionalParameters().get("loginType");
        String loginType = (loginTypeObj != null) ? loginTypeObj.toString() : "client";

        UserProfile userProfile = OAuthAttributes.extract(
                userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        User user = saveOrUpdateUser(userProfile, loginType);

        String authority = (user.getRole() != null)
                ? "ROLE_" + user.getRole().name()
                : "ROLE_" + user.getUserType().name();

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(authority)),
                oAuth2User.getAttributes(),
                userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName()
        );
    }

    private User saveOrUpdateUser(UserProfile profile, String loginType) {
        User.UserType userType = "admin".equalsIgnoreCase(loginType)
                ? User.UserType.ADMIN
                : User.UserType.CLIENT;

        return userRepository.findByProviderAndProviderId(profile.getProvider(), profile.getProviderId())
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .username(profile.getUsername())
                                .email(profile.getEmail())
                                .provider(profile.getProvider())
                                .providerId(profile.getProviderId())
                                .userType(userType)
                                .role(null)
                                .build()
                ));
    }

    @Transactional
    public User completeClientInfo(String providerId, ClientAdditionalInfo request) {
        User user = userRepository.findByProviderAndProviderId(User.Provider.GOOGLE, providerId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        user.completeClientInfo(
                request.getUsername(),
                request.getStudentNumber(),
                request.getRole()
        );
        return user;
    }

    @Transactional
    public User completeAdminInfo(String providerId, AdminAdditionalInfo request) {
        User user = userRepository.findByProviderAndProviderId(User.Provider.GOOGLE, providerId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        user.completeAdminInfo(
                request.getUsername(),
                request.getRole()
        );
        return user;
    }
}