package com.payhere.phtest.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenInfo {

    private final String grantType;

    private final String authorizationType;

    private final String accessToken;

    private final String refreshToken;

    private final Long accessTokenExpiresIn;

}
