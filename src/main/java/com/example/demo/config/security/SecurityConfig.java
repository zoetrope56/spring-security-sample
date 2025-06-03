package com.example.demo.config.security;

import com.example.demo.config.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)                                                          // CSRF 보호 비활성화
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))                              // CORS 커스텀 설정 적용
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())                                   // 우선 모든 요청에 대한 허용
                .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class)                     // JWT 인증 (커스텀 필터)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   // 세션 미사용 (JWT 사용)
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)      // 사용자 인증(커스텀 필터)
                .formLogin(AbstractHttpConfigurer::disable)                                                     // 폼 로그인 비활성화
                .build();

    }



//
//    /**
//     * 2. authenticate 의 인증 메서드를 제공하는 매니져로'Provider'의 인터페이스를 의미한다.
//     * 이 메서드는 인증 매니저를 생성한다. 인증 매니저는 인증 과정을 처리하는 역할을 한다.
//     * 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(CustomAuthenticationProvider customAuthenticationProvider) {
//        return new ProviderManager(Collections.singletonList(customAuthenticationProvider));
//    }
//
//    /**
//     * 3. '인증' 제공자로 사용자의 이름과 비밀번호가 요구된다.
//     * 이 메서드는 사용자 정의 인증 제공자를 생성한다. 인증 제공자는 사용자 이름과 비밀번호를 사용하여 인증을 수행한다.
//     * 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
//     */
//    @Bean
//    public CustomAuthenticationProvider customAuthenticationProvider(UserDetailsService userDetailsService) {
//        return new CustomAuthenticationProvider(
//                userDetailsService
//        );
//    }
//
//    /**
//     * 4. Spring Security 기반의 사용자의 정보가 맞을 경우 수행이 되며 결과값을 리턴해주는 Handler
//     * customLoginSuccessHandler: 이 메서드는 인증 성공 핸들러를 생성한다. 인증 성공 핸들러는 인증 성공시 수행할 작업을 정의한다.
//     */
//    @Bean
//    public CustomAuthSuccessHandler customLoginSuccessHandler() {
//        return new CustomAuthSuccessHandler();
//    }
//
//    /**
//     * 5. Spring Security 기반의 사용자의 정보가 맞지 않을 경우 수행이 되며 결과값을 리턴해주는 Handler
//     * customLoginFailureHandler: 이 메서드는 인증 실패 핸들러를 생성한다. 인증 실패 핸들러는 인증 실패시 수행할 작업을 정의한다.
//     */
//    @Bean
//    public CustomAuthFailureHandler customLoginFailureHandler() {
//        return new CustomAuthFailureHandler();
//    }


    /**
     * 이 메서드는 정적 자원에 대해 보안을 적용하지 않도록 설정한다.
     * 정적 자원은 보통 HTML, CSS, JavaScript, 이미지 파일 등을 의미하며, 이들에 대해 보안을 적용하지 않음으로써 성능을 향상시킬 수 있다.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(
                        "/swagger-ui/**",
                        "/token/**"
                ); // 토큰 인증 무시 uri 지정
    }

    /**
     * "JWT 토큰을 통하여서 사용자를 인증한다." -> 이 메서드는 JWT 인증 필터를 생성한다.
     * JWT 인증 필터는 요청 헤더의 JWT 토큰을 검증하고, 토큰이 유효하면 토큰에서 사용자의 정보와 권한을 추출하여 SecurityContext에 저장한다.
     */
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(UserDetailsService userDetailsService) {
        return new JwtAuthorizationFilter(userDetailsService);
    }

}
