package com.joel.ecommerce.utils;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public class ErrorMessage implements Serializable {
	
	private static final long serialVersionUID = -5282315676214628830L;

	@SuppressWarnings("unused")
	private String path;
	
	@SuppressWarnings("unused")
	private HttpStatus status;
	
	@SuppressWarnings("unused")
	private String message;
}
