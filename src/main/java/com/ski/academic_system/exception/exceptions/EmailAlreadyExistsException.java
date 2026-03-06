package com.ski.academic_system.exception.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
	public EmailAlreadyExistsException() {
		super("Este email já está cadastrado.");
	}
}
