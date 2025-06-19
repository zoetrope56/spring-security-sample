package com.example.demo.config.component;

import com.example.demo.api.dto.UserDetailDto;
import com.example.demo.api.entity.user.User;
import com.example.demo.common.enumulation.UserGrant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

/**
 * JWT 관련 토큰 Util
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private static Key secretKey;

    @Value("${auth.token.jwt.expiration-time}") // 인증토큰 생성 시 적용할 토큰 만료시간 (accessToken)
    private String expirationTime;

    @Value("${auth.token.jwt.refresh-expiration-time}") // 인증토큰 생성 시 적용할 토큰 만료시간 (refreshToken)
    private String refreshExpirationTime;

    /**
     * secretKey 변수값에 환경 변수에서 불러온 SECRET_KEY를 주입합니다.
     *
     * @param jwtSecretKey JWT 환경 변수
     */
    public JwtTokenProvider(@Value("${auth.token.jwt.secret-key}") String jwtSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * token 생성
     *
     * @param user token 생성 시 참고할 사용자 정보
     * @return access token
     */
    public String generateToken(User user) {
        val expirationDate = new Date(System.currentTimeMillis() + Long.parseLong(this.expirationTime));
        // payload
        val claimsMap = new HashMap<String, Object>();
        claimsMap.put("userSeq", user.getUserSeq());
        claimsMap.put("userId", user.getUserId());
        claimsMap.put("userName", user.getUserName());
        claimsMap.put("userGrant", user.getUserGrant());

        return Jwts.builder()
                .setSubject("accessToken:" + user.getUserSeq())
                .setClaims(claimsMap)
                .setExpiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * refresh token 생성
     *
     * @param user token 생성 시 참고할 사용자 정보
     * @return refresh token
     */
    public String generateRefreshToken(User user) {
        val refreshExpirationDate = new Date(System.currentTimeMillis() + Long.parseLong(this.refreshExpirationTime));

        return Jwts.builder()
                .setSubject("refreshToken:" + user.getUserSeq())
                .setExpiration(refreshExpirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * @param token 토큰
     * @return 토큰 유효 여부 반환
     */
    public boolean isValidToken(String token) {
        try {
            return true;
        } catch (SecurityException | MalformedJwtException | DecodingException e) {
            log.warn("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty.", e);
        } catch (Exception e) {
            log.error("Unhandled JWT exception", e);
        }
        return false;
    }


    /**
     * 'Claims' 내에서 '사용자 아이디'를 반환하는 메서드
     *
     * @param token : 토큰
     * @return String : 사용자 아이디
     */
    public String getUserIdFromToken(String token) {
        return parseClaims(token).user().getUserId();
    }


    /**
     * 'JWT' 내에서 'Claims' 정보를 반환하는 메서드
     *
     * @param token : 토큰
     * @return Claims : Claims
     */
    public UserDetailDto parseClaims(String token) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new UserDetailDto(
                User.builder()
                        .userSeq(Long.valueOf(claims.get("userSeq").toString()))
                        .userId(String.valueOf(claims.get("userId")))
                        .userName(String.valueOf(claims.get("userName")))
                        .userGrant(UserGrant.valueOf(String.valueOf(claims.get("userGrant"))))
                        .build()
        );
    }

}

