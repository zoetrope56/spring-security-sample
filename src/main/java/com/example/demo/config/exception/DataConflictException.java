package com.example.demo.config.exception;

import com.example.demo.common.enumulation.ResponseCode;

public class DataConflictException extends PhTestException {

    public DataConflictException(String message, ResponseCode code) {
        super(message, code);
    }

    public DataConflictException(String message) {
        super(message, ResponseCode.CONFLICT_DATA_ERROR);
    }

}
