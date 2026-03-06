package com.ski.academic_system.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ski.academic_system.dto.LoginRequest;
import com.ski.academic_system.dto.LoginResponse;
import com.ski.academic_system.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public LoginResponse login(LoginRequest request) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					request.login(), //login: email ou RA do usuário
					request.password()
				)
			);
		
		CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
		
		String token = jwtService.generateToken(user);
		
		return new LoginResponse(token);
	}
	
		
}
