package com.example.gif.auth.global.security;

import com.example.gif.auth.domain.service.OAuth2Service;
import com.example.gif.auth.global.security.jwt.JwtAuthenticationFilter;
import com.example.gif.auth.global.security.oauth.CustomAuthorizationRequestResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2Service oAuth2Service;
    private final CustomOAuth2SuccessHandler successHandler;
    private final CustomAuthorizationRequestResolver customAuthorizationRequestResolver;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/oauth2/**", "/oauth/loginInfo").permitAll()
                        .requestMatchers("/auth/admin/login", "/auth/client/login").permitAll()

                        .requestMatchers("/auth/client/additional-info").hasRole("GUEST")
                        .requestMatchers("/auth/admin/additional-info").hasRole("GUEST")

                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/oauth2/authorization")
                                .authorizationRequestResolver(customAuthorizationRequestResolver)
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2Service)
                        )
                        .successHandler(successHandler)
                )

                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
