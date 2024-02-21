package com.payhere.phtest.config.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.phtest.api.user.vo.UserVo;
import com.payhere.phtest.common.dto.UserDetailDto;
import com.payhere.phtest.common.enumulation.ResponseCode;
import com.payhere.phtest.config.exception.TokenAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String TOKEN_BODY_INFO_KEY = "info";

    // 인증토큰 secret key
    @Value("${auth.token.jwt.secret-key}")
    private String secretKey;

    // 인증토큰 생성 시 적용할 토큰 만료시간 (기본값 30분)
    @Value("${auth.token.jwt.expiration-time}")
    private String expirationTime;

    // 인증토큰 생성 시 적용할 토큰 만료시간 (refresh) (기본값 14일)
    @Value("${auth.token.jwt.refresh-expiration-time}")
    private String refreshExpirationTime;

    private final ObjectMapper objectMapper;

    /**
     * 인증 토큰 생성
     *
     * @param userVo 사용자 정보
     * @return 인증 토큰
     */
    public String generateToken(UserVo userVo) {
        // 만료일
        final var expirationDate = new Date(System.currentTimeMillis() + Long.parseLong(this.expirationTime));

        // 토큰 header
        final var header = new HashMap<String, Object>();
        header.put("typ", "JWT");
        header.put("alg", SignatureAlgorithm.HS256.getValue());
        header.put("regDate", System.currentTimeMillis());

        // 토큰 body
        final var claimsMap = new HashMap<String, Object>();
        try {
            claimsMap.put(TOKEN_BODY_INFO_KEY, this.objectMapper.writeValueAsString(userVo));
        } catch (JsonProcessingException e) {
            if (log.isErrorEnabled()) {
                log.error("토큰 생성 오류 사용자 정보 --> {}", userVo);
                log.error(e.getMessage(), e);
            }
            throw new TokenAuthenticationException(ResponseCode.UNAUTHORIZED_INVALID_TOKEN.getMessage());
        }

        // 암호화 key spec
        final var keySpec = new SecretKeySpec(DatatypeConverter.parseBase64Binary(this.secretKey), SignatureAlgorithm.HS256.getJcaName());
        final var jwtBuilder =
                Jwts.builder()
                        .signWith(keySpec)
                        .setSubject("payhere-test:" + userVo.getUserNo())
                        .setExpiration(expirationDate)
                        .setHeader(header)
                        .setClaims(claimsMap);

        if (log.isDebugEnabled()) log.info("JWT token  :::::: {}", jwtBuilder);
        return jwtBuilder.compact();
    }

    /**
     * refresh token 생성
     *
     * @return refresh token
     */
    public String generateRefreshToken() {
        // 만료일
        final var expirationDate = new Date(System.currentTimeMillis() + Long.parseLong(this.refreshExpirationTime));

        // 토큰 header
        final var header = new HashMap<String, Object>();
        header.put("typ", "JWT");
        header.put("alg", SignatureAlgorithm.HS256.getValue());
        header.put("regDate", System.currentTimeMillis());

        // 암호화 key spec
        final var keySpec = new SecretKeySpec(DatatypeConverter.parseBase64Binary(this.secretKey), SignatureAlgorithm.HS256.getJcaName());
        final var jwtBuilder =
                Jwts.builder()
                        .signWith(keySpec)
                        .setSubject("payhere-test:refresh-token")
                        .setExpiration(expirationDate)
                        .setHeader(header);

        if (log.isDebugEnabled()) log.info("JWT refresh token  :::::: {}", jwtBuilder);
        return jwtBuilder.compact();
    }

    /**
     * 인증 토큰 검사
     *
     * @param token 인증 토큰
     * @return 검사 결과 (성공 시 사용자 정보 반환)
     */
    public UserDetailDto validateToken(String token) {
        final Claims claims =
                Jwts.parserBuilder()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(this.secretKey))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

        if (log.isDebugEnabled()) {
            log.debug(claims.toString());
        }

        try {
            final var infoString = (String) claims.get(TOKEN_BODY_INFO_KEY);
            if (StringUtils.isNotBlank(infoString)) {
                final var userVo = this.objectMapper.readValue(infoString, UserVo.class);

                return new UserDetailDto(userVo);
            } else {
                throw new TokenAuthenticationException("유효한 토큰이 아닙니다.");
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("인증 토큰 오류 발생. token --> {}", token);
                log.error(e.getMessage(), e);
            }
            throw new TokenAuthenticationException("유효한 토큰이 아닙니다.");
        }
    }

    /**
     * refresh token 유효성 검사.
     *
     * @param token refresh token
     */
    public void validateRefreshToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(this.secretKey))
                .build()
                .parseClaimsJws(token);
    }
}
