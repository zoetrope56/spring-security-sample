package com.example.demo.config;

import com.example.demo.api.mapper.UserMapper;
import com.example.demo.config.component.JwtTokenProvider;
import com.example.demo.config.filter.AuthenticationExceptionFilter;
import com.example.demo.config.filter.AuthenticationFilter;
import com.example.demo.config.filter.AuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthConfig extends AbstractHttpConfigurer<AuthConfig, HttpSecurity> {

    private final AuthenticationManager authenticationManager;

    private final ObjectMapper objectMapper;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate redisTemplate;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    /**
     * 인증을 위한 필터 등록
     * @param http security http
     */
    @Override
    public void configure(HttpSecurity http) {
        val authenticationExceptionFilter = new AuthenticationExceptionFilter(this.objectMapper);
//        val authenticationFilter = new AuthenticationFilter(this.jwtTokenProvider, this.redisTemplate, this.userMapper);
        val authorizationFilter = new AuthorizationFilter(this.authenticationManager, this.jwtTokenProvider, this.redisTemplate, this.userMapper, this.passwordEncoder); // 인증 - 권한 관련 코드

        http.addFilterBefore(authenticationExceptionFilter, AuthenticationFilter.class)
                .addFilterBefore(authorizationFilter, BasicAuthenticationFilter.class);

    }

}
