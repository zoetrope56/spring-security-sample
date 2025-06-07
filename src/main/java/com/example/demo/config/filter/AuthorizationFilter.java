package com.example.demo.config.filter;

import com.example.demo.api.dto.UserDetailDto;
import com.example.demo.api.mapper.UserMapper;
import com.example.demo.common.constants.CommonConstants;
import com.example.demo.config.component.JwtTokenProvider;
import com.example.demo.config.exception.TokenAuthenticationException;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate redisTemplate;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    /**
     * 필터 생성자
     *
     * @param authenticationManager 인증 생성자
     * @param jwtTokenProvider      JWT 처리 공통 provider
     * @param redisTemplate         redisTemplate
     * @param userMapper            userMapper
     * @param passwordEncoder       passwordEncoder
     */
    public AuthorizationFilter(final AuthenticationManager authenticationManager, final JwtTokenProvider jwtTokenProvider, RedisTemplate redisTemplate, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws IOException {
        final String header = request.getHeader("Authorization");

        try {

            if (header.startsWith("Bearer ")) {
                final String token = StringUtils.strip(header.replace("Bearer ", "")); // 로그인

                // Redis에 해당 accessToken logout 여부를 확인
                String isLogout = (String) redisTemplate.opsForValue().get(token);
                if (!ObjectUtils.isEmpty(isLogout)) {
                    // 로그아웃 처리로 만료된 토큰일 경우
                    throw new TokenAuthenticationException("만료된 토큰입니다.");
                }

                UserDetailDto userDetail = this.jwtTokenProvider.parseClaims(token);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("로그인 인증 SUCCESS -> userNm: {}", userDetail.getUsername());
                request.setAttribute(CommonConstants.COMMON_REQUEST_PRINCIPAL_DETAILS, authentication.getPrincipal());
            }
            log.info("security context principal : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            log.info("authorities context : {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());

            filterChain.doFilter(request, response);

        } catch (NullPointerException e) {
            log.error(" NullPointerException : AuthorizationFilter FAIL -> {}", e.getMessage());
            throw new TokenAuthenticationException("토큰이 존재하지 않습니다.");
        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException : AuthorizationFilter FAIL -> {}", e.getMessage());
            throw new TokenAuthenticationException("만료된 토큰입니다.");
        } catch (JwtException | ServletException e) {
            log.error("JwtException : AuthorizationFilter FAIL -> {}", e.getMessage());
            throw new TokenAuthenticationException("잘못된 토큰입니다.");
        }

    }

}
