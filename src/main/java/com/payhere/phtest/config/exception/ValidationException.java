package com.payhere.phtest.config.exception;

import com.payhere.phtest.common.enumulation.ResponseCode;

public class ValidationException extends PhTestException {

	public ValidationException(String message, ResponseCode code) {
		super(message, code);
	}

	public ValidationException(String message) {
		super(message, ResponseCode.INVALID_DATA_ERROR);
	}
}
