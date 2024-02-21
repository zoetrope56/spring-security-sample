package com.payhere.phtest.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.phtest.api.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthConfig extends AbstractHttpConfigurer<AuthConfig, HttpSecurity> {

}
