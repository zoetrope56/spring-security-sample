package com.example.demo.config.exception;

import com.example.demo.common.enumulation.ResponseCode;

public class DataNotFoundException extends SecurityTestException {

    public DataNotFoundException(String message, ResponseCode code) {
        super(message, code);
    }

    public DataNotFoundException(String message) {
        super(message, ResponseCode.NOT_FOUND_DATA);
    }

}
