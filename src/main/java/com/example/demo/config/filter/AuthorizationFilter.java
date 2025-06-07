package com.example.demo.config.filter;

import com.example.demo.api.mapper.UserMapper;
import com.example.demo.common.enumulation.ResponseCode;
import com.example.demo.config.component.JwtTokenProvider;
import com.example.demo.config.exception.DataNotFoundException;
import com.example.demo.config.exception.InternalServerException;
import com.example.demo.config.exception.ValidationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
    public AuthorizationFilter(final AuthenticationManager authenticationManager, final JwtTokenProvider jwtTokenProvider, RedisTemplate redisTemplate, RedisTemplate redisTemplate1, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate1;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
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
                if (jwtTokenProvider.isValidToken(token)) {
                    // 추출한 토큰을 기반으로 사용자 아이디를 반환받는다.
                    String loginId = jwtTokenProvider.getUserIdFromToken(token);
                    log.debug("[+] loginId Check: {}", loginId);

                    //  사용자 아이디가 존재하는지에 대한 여부를 체크한다.
                    if (loginId != null && !loginId.equalsIgnoreCase("")) {
                        UserDetails userDetails =
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
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

        }
    }

}
