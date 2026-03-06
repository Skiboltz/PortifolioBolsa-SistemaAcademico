package com.ski.academic_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ski.academic_system.dto.UserDTO;
import com.ski.academic_system.dto.UserRegisterRequest;
import com.ski.academic_system.model.User;
import com.ski.academic_system.repository.UserRepository;
import com.ski.academic_system.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ApiController {
	
	private final UserService userService;
	private final UserRepository userRepository;

	@GetMapping("/me")
	public ResponseEntity<?> me(Authentication authentication) {
		
		if(authentication == null) {
			ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		String email = authentication.getName();
		
		User user = userRepository.findByEmail(email).orElseThrow();
		
		return ResponseEntity.ok(new UserDTO(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getRA(),
				user.getRole().authority()
					)
				);
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails){
		if(userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
		return ResponseEntity.ok(userService.toDTO(userService.getUserById(id)));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserRegisterRequest> registerUser(@RequestBody UserRegisterRequest user, Authentication authentication){
		UserRegisterRequest registered = userService.registerUser(user, authentication);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(registered);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user, Authentication authentication){
		return ResponseEntity.ok(userService.updateUser(id, user));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
