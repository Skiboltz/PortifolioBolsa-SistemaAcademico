package com.ski.academic_system.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record ApiError (

	int status,
	String error,
	String message,
	LocalDateTime timestamp
	
) {}
