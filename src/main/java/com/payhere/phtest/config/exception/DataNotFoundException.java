package com.payhere.phtest.config.exception;

import com.payhere.phtest.common.enumulation.ResponseCode;

public class DataNotFoundException extends PhTestException {

    public DataNotFoundException(String message, ResponseCode code) {
        super(message, code);
    }

    public DataNotFoundException(String message) {
        super(message, ResponseCode.NOT_FOUND_DATA);
    }

}
