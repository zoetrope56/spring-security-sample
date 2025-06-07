package com.example.demo.config.filter;

import com.example.demo.config.exception.SecuritySampleException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 인증 관련 필터에서 발생하는 예외 처리 필터 (필터에서 발생하는 예외를 공통으로 처리하기 위한 필터)
 *
 * @author 정재요
 * @date 2023. 03. 28
 */
@Slf4j
@RequiredArgsConstructor
public class AuthenticationExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecuritySampleException e) {
            log.error("CommonException -> {}", e.getMessage());

            val status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            this.objectMapper.writeValue(response.getOutputStream(), e.getMessage());
        } catch (UsernameNotFoundException e) {
            log.error("UsernameNotFoundException -> {}", e.getMessage());

            val status = HttpStatus.NOT_FOUND;
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            this.objectMapper.writeValue(response.getOutputStream(), e.getMessage());
        } catch (Exception e) {
            log.error("Exception -> {}", e.getMessage());

            val status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            this.objectMapper.writeValue(response.getOutputStream(), e.getMessage());
        }
    }
}
