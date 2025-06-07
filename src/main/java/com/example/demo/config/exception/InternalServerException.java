package com.example.demo.config.exception;

import com.example.demo.common.enumulation.ResponseCode;

public class InternalServerException extends SecurityTestException {

	public InternalServerException(String message, ResponseCode code) {
		super(message, code);
	}

	public InternalServerException(String message) {
		super(message, ResponseCode.INTERNAL_SERVER_ERROR);
	}

}
