package com.example.demo.config.exception;

import com.example.demo.common.enumulation.ResponseCode;

public class ValidationException extends PhTestException {

	public ValidationException(String message, ResponseCode code) {
		super(message, code);
	}

	public ValidationException(String message) {
		super(message, ResponseCode.INVALID_DATA_ERROR);
	}
}
