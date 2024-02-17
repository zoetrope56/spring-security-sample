package com.payhere.phtest.config.exception;

import com.payhere.phtest.common.enumulation.ResponseCode;

public class InternalServerException extends PhTestException {

	public InternalServerException(String message, ResponseCode code) {
		super(message, code);
	}

	public InternalServerException(String message) {
		super(message, ResponseCode.INTERNAL_SERVER_ERROR);
	}

}
