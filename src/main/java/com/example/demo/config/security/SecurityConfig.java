package com.example.demo.config.security;

import com.example.demo.config.AuthConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * security 설정 적용
     *
     * @param http http 설정
     * @return 필터 체인
     * @throws Exception 공통 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthConfig authConfig) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 미사용 (JWT 사용)
                .formLogin(formLogin -> formLogin.disable())
                .logout(logout -> logout.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 우선 모든 요청에
                .with(authConfig, Customizer.withDefaults());
        return http.build();
    }

    /**
     * 적용한 security 에서 적용 제외할 uri 패턴 적용
     *
     * @return security 설정
     */
    @Bean
    public WebSecurityCustomizer webSecurity() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(
                        "/swagger-ui/**",
                        "/token/**"
                );
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        val builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        return builder.build();
    }

}
