package com.payhere.phtest.config.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.payhere.phtest.api.user.vo.UserVo;
import com.payhere.phtest.common.enumulation.ResponseCode;
import com.payhere.phtest.config.exception.InternalServerException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authRequest;

        try {
            authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
        } catch (Exception e) {
            throw new InternalServerException(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
        }

        // Authentication 객체를 반환한다.
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws Exception {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

            UserVo user = objectMapper.readValue(request.getInputStream(), UserVo.class);
            log.debug("1.CustomAuthenticationFilter :: loginId: " + user.getUserId());

            /**
             * ID, PW를 기반으로 UsernamePasswordAuthenticationToken 토큰을 발급한다.
             * UsernamePasswordAuthenticationToken 객체가 처음 생성될 때 authenticated 필드는 기본적으로 false로 설정된다.
             */
            return new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerException(ResponseCode.INTERNAL_SERVER_ERROR.getMessage()) {
            };
        }

    }
}
