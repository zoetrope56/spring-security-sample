package com.payhere.phtest.config.exception;

import com.payhere.phtest.common.enumulation.ResponseCode;

public class TokenAuthenticationException extends PhTestException {

    public TokenAuthenticationException(String message, ResponseCode responseCode) {
        super(message, responseCode);
    }

    public TokenAuthenticationException (String message) {
        super(message, ResponseCode.UNAUTHORIZED_INVALID_TOKEN);
    }

}
