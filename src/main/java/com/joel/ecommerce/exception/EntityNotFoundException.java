package com.joel.ecommerce.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6291769438341789221L;

	public EntityNotFoundException() {
		
		super();
	}
	
	public EntityNotFoundException(String message) {
		
		super(message);
	}
}
