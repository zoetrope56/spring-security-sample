package com.example.demo.config.exception;

import com.example.demo.common.enumulation.ResponseCode;
import lombok.Getter;

@Getter
public abstract class SecuritySampleException extends RuntimeException {

    private final ResponseCode responseCode;

    public SecuritySampleException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public int getStatus() {
        return this.responseCode.getCode();
    }

    public String getMessage() {
        return this.responseCode.getMessage();
    }

}
