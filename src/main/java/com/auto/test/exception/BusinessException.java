package com.auto.test.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -2331579777990944996L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

}
