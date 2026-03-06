/*
package com.ski.academic_system.test;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ski.academic_system.model.Role;
import com.ski.academic_system.model.User;
import com.ski.academic_system.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer {

	private final UserRepository repo;
	private final PasswordEncoder psw;
	
	@PostConstruct
	public void init() {
		
		if(repo.count() == 0) {
			User admin = new User();
			admin.setName("Master Admin");
			admin.setEmail("admin@system.com");
			admin.setPassword(psw.encode("admin123"));
			admin.setRA("0001");
			admin.setRole(Role.ADMIN);
			repo.save(admin);
		}
		
	}
	
}
*/
