package com.example.demo.config.exception;

import com.example.demo.common.enumulation.ResponseCode;

public class TokenAuthenticationException extends SecurityTestException {

    public TokenAuthenticationException(String message, ResponseCode responseCode) {
        super(message, responseCode);
    }

    public TokenAuthenticationException (String message) {
        super(message, ResponseCode.UNAUTHORIZED_INVALID_TOKEN);
    }

}
