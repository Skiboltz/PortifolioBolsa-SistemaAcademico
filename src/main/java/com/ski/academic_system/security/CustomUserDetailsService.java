package com.ski.academic_system.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ski.academic_system.model.User;
import com.ski.academic_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmailOrRA(login, login)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		return new CustomUserDetails(user);
	}
	
public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		return new CustomUserDetails(user);
	}


		
}
