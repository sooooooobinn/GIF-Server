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
        String loginType = (loginTypeObj != null) ? loginTypeObj.toString() : null;

        if (loginType == null) {
            loginType = getLoginTypeFromRequest();
        }

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        UserProfile userProfile = OAuthAttributes.extract(registrationId, oAuth2User.getAttributes());

        saveOrUpdateUser(userProfile, loginType);

        Map<String, Object> customAttribute = new ConcurrentHashMap<>(oAuth2User.getAttributes());
        customAttribute.put("loginType", loginType);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_GUEST")),
                customAttribute,
                userNameAttributeName
        );
    }

    private void saveOrUpdateUser(UserProfile profile, String loginType) {
        User.UserType userType = "admin".equalsIgnoreCase(loginType)
                ? User.UserType.ADMIN
                : User.UserType.CLIENT;

        userRepository.findByProviderAndProviderId(profile.getProvider(), profile.getProviderId())
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .username(profile.getUsername())
                                .email(profile.getEmail())
                                .provider(profile.getProvider())
                                .providerId(profile.getProviderId())
                                .userType(userType)
                                .build()
                ));
    }

    @Transactional
    public void completeClientInfo(String providerId, ClientAdditionalInfo request) {
        User user = userRepository.findByProviderAndProviderId(User.Provider.GOOGLE, providerId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        user.completeClientInfo(
                request.getUsername(),
                request.getStudentNumber(),
                request.getRole()
        );
    }

    @Transactional
    public void completeAdminInfo(String providerId, AdminAdditionalInfo request) {
        User user = userRepository.findByProviderAndProviderId(User.Provider.GOOGLE, providerId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        user.completeAdminInfo(
                request.getUsername(),
                request.getRole()
        );
    }

    private String getLoginTypeFromRequest() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String loginType = (String) request.getSession().getAttribute("loginType");
            return (loginType != null) ? loginType : "client";
        } catch (Exception e) {
            return "client";
        }
    }
}