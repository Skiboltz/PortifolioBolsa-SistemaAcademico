package com.ski.academic_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ski.academic_system.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByName(String name);
	
	Optional<User> findByEmailOrRA(String email, String RA);
	
	Optional<User> findById(Long id);
	
	void deleteById(Long id);
	
	boolean existsByEmail(String email);
	
	boolean existsByRA(String RA);
	
}
