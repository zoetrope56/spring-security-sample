package com.payhere.phtest.config.exception;

import com.payhere.phtest.common.enumulation.ResponseCode;

public class DataConflictException extends PhTestException {

    public DataConflictException(String message, ResponseCode code) {
        super(message, code);
    }

    public DataConflictException(String message) {
        super(message, ResponseCode.CONFLICT_DATA_ERROR);
    }

}
