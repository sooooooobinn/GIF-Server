package com.example.gif.auth.global.security.oauth;

import com.example.gif.auth.domain.dto.UserProfile;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {

    GOOGLE("google", (attribute) -> {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername((String)attribute.get("name"));
        userProfile.setEmail((String)attribute.get("email"));

        return userProfile;
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
