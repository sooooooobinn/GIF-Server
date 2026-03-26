package com.example.gif.auth.global.security.oauth;

import com.example.gif.auth.domain.dto.UserProfile;
import com.example.gif.auth.domain.entity.User;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {

    GOOGLE("google", (attribute) -> {
        return UserProfile.builder()
                .username((String) attribute.get("name"))
                .email((String) attribute.get("email"))
                .provider(User.Provider.GOOGLE)
                .providerId((String) attribute.get("sub"))
                .build();
    });

    private final String registerationId;
    private final Function<Map<String, Object>, UserProfile> of;

    OAuthAttributes(String registerationId, Function<Map<String, Object>, UserProfile> of) {
        this.registerationId = registerationId;
        this.of = of;
    }

    public static UserProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(value -> registrationId.equals(value.registerationId))
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .of.apply(attributes);
    }
}
