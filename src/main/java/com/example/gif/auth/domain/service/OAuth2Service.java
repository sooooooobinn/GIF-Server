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

        // 💡 수정 포인트: userRequest.getAttributes()는 존재하지 않습니다.
        // 대신 Resolver에서 attributes에 담았다면 아래와 같이 꺼내야 합니다.
        Object loginTypeObj = userRequest.getAdditionalParameters().get("loginType");
        String loginType = (loginTypeObj != null) ? loginTypeObj.toString() : null;

        // 만약 위 코드가 안 된다면 (버전에 따라 다름), 아래 코드로 시도하세요.
        if (loginType == null) {
            loginType = (String) userRequest.getAdditionalParameters().get("loginType");
        }

        System.out.println("===> 최종 추출된 loginType: " + loginType);

        if (loginType == null) loginType = "client";

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

        User.UserType userType =
                "admin".equalsIgnoreCase(loginType)
                        ? User.UserType.ADMIN
                        : User.UserType.CLIENT;

        userRepository.findByProviderAndProviderId(
                        profile.getProvider(),
                        profile.getProviderId()
                )
                .orElseGet(() ->
                        userRepository.save(
                                User.builder()
                                        .username(profile.getUsername())
                                        .email(profile.getEmail())
                                        .provider(profile.getProvider())
                                        .providerId(profile.getProviderId())
                                        .userType(userType)
                                        .role(null)
                                        .build()
                        )
                );
    }

    @Transactional
    public void completeClientInfo(Long userId, ClientAdditionalInfo request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        user.completeClientInfo(
                request.getUsername(),
                request.getStudentNumber(),
                request.getRole()
        );
    }

    @Transactional
    public void completeAdminInfo(Long userId, AdminAdditionalInfo request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        user.completeAdminInfo(
                request.getUsername(),
                request.getRole()
        );
    }

    private String getLoginTypeFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String loginType = (String) request.getSession().getAttribute("loginType");

        System.out.println("로그인 시도 타입: " + loginType);

        return (loginType != null) ? loginType : "client";
    }
}