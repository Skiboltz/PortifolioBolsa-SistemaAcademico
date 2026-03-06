package com.ski.academic_system.dto;


public record LoginRequest (
	
	String login, // login: RA ou email do usuário
	String password
	
) {}
