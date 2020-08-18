package com.b3.users.usecase.exceptions;

import java.util.Map;

import lombok.Getter;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3507037978417195561L;

	@Getter
	private Map<String, String> errorsMap;

	public ValidationException(Map<String, String> errors) {
		this.errorsMap = errors;
	}

}