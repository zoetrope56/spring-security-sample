package com.payhere.phtest.config.filter;

import com.payhere.phtest.common.enumulation.ResponseCode;
import com.payhere.phtest.common.util.TokenUtils;
import com.payhere.phtest.config.exception.DataNotFoundException;
import com.payhere.phtest.config.exception.InternalServerException;
import com.payhere.phtest.config.exception.ValidationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 토큰이 필요하지 않은 API URL에 대해서 배열로 구성한다.
        List<String> list = Arrays.asList("/user/login", "/login", "/css/**", "/js/**", "/images/**");

        // 2. 토큰이 필요하지 않은 API URL의 경우 -> 로직 처리없이 다음 필터로 이동한다.
        if (list.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. OPTIONS 요청일 경우 -> 로직 처리 없이 다음 필터로 이동
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        // [STEP.1] Client에서 API를 요청할때 쿠키를 확인한다.
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        try {
            // 쿠키 내에 토큰이 존재하는 경우
            if (token != null && !token.equalsIgnoreCase("")) {
                //  쿠키 내에있는 토큰이 유효한지 여부를 체크한다.
                if (TokenUtils.isValidToken(token)) {
                    // 추출한 토큰을 기반으로 사용자 아이디를 반환받는다.
                    String loginId = TokenUtils.getUserIdFromToken(token);
                    log.debug("[+] loginId Check: " + loginId);

                    //  사용자 아이디가 존재하는지에 대한 여부를 체크한다.
                    if (loginId != null && !loginId.equalsIgnoreCase("")) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        filterChain.doFilter(request, response);
                    } else {
                        throw new DataNotFoundException(ResponseCode.NOT_FOUND_DATA.getMessage());
                    }
                } else {
                    //  토큰이 유효하지 않은 경우
                    throw new ValidationException((ResponseCode.UNAUTHORIZED_INVALID_TOKEN.getMessage()));
                }
            } else {
                //  토큰이 존재하지 않는 경우
                throw new InternalServerException((ResponseCode.INTERNAL_SERVER_ERROR.getMessage()));
            }
        } catch (Exception e) {
            // 로그에만 해당 메시지를 출력합니다.
            log.error("{}", e.getMessage());

            // 클라이언트에게 전송할 고정된 메시지
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");

        }
    }

}
