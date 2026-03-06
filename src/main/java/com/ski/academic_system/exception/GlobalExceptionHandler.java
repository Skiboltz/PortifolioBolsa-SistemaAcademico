package com.ski.academic_system.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ski.academic_system.exception.exceptions.EmailAlreadyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private ResponseEntity<ApiError> buildError(
			HttpStatus status,
			String error,
			String message
			){
		
		ApiError apiError = new ApiError(
				status.value(),
				error,
				message,
				LocalDateTime.now()
				);
		
		return ResponseEntity.status(status).body(apiError);
		
	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ApiError> handleEmailAlreadyExists(EmailAlreadyExistsException exception){
		return buildError(
				HttpStatus.CONFLICT,
				"Email já cadastrado",
				exception.getMessage()
				);
	}
	
	@ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
	public ResponseEntity<ApiError> handleAuthErrors(Exception exception){
		return buildError(
				HttpStatus.UNAUTHORIZED,
				"Login ou senha incorretos",
				exception.getMessage()
				);
	}
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException exception){
    	return buildError(
    			HttpStatus.BAD_REQUEST,
    			"Requisição inválida",
    			exception.getMessage()
    			);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception exception){
    	return buildError(
    			HttpStatus.INTERNAL_SERVER_ERROR,
    			"Erro interno",
    			"Ocorreu um erro inesperado no servidor."
    			);
    }
    
}
