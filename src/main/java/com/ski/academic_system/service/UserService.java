package com.ski.academic_system.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ski.academic_system.dto.UserDTO;
import com.ski.academic_system.dto.UserRegisterRequest;
import com.ski.academic_system.exception.exceptions.EmailAlreadyExistsException;
import com.ski.academic_system.model.Role;
import com.ski.academic_system.model.User;
import com.ski.academic_system.repository.UserRepository;
import com.ski.academic_system.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserRegisterRequest registerUser(UserRegisterRequest request, Authentication authentication) {
		
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		
		Role creatorRole = currentUser.getUser().getRole();
		Role newUserRole = Role.valueOf(request.role().substring(5));
		
		validatePermission(creatorRole, newUserRole);
		
		validateUniqueness(request);
		
		User user = new User();
		user.setName(request.name());
		user.setEmail(request.email());
		
		String RA;
		
		
		do {
		RA = UUID.randomUUID().toString().substring(0, 15);
		} while(userRepository.existsByRA(RA));
		
		user.setRA(RA);
		
		String tempPassword = UUID.randomUUID().toString().substring(0, 10);
		
		user.setPassword(passwordEncoder.encode(tempPassword));
		System.out.println(tempPassword);
		user.setRole(newUserRole);
		
		userRepository.save(user);
		
		return request;
		
	}
	
	public UserDTO updateUser(Long id, UserDTO updateUser) {
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		User updatedUser = fromDTO(updateUser);
		
		user.setName(updatedUser.getName());
		user.setEmail(updatedUser.getEmail());
		user.setRole(updatedUser.getRole());
		user.setRA(updatedUser.getRA());
		
		System.out.println(user);
		
		//Adicionar validação
		
		userRepository.save(user);
		
		return toDTO(user);
		
	}
	
	private void validatePermission(Role creator, Role roleRequest) {
		
		if(creator == Role.ADMIN) {
			return;
		}
		
		if(creator == Role.TEACHER) {
			if(roleRequest == Role.STUDENT) {
				return;
			}
		}
		
		throw new IllegalArgumentException();
			
	}
	
	private void validateUniqueness(UserRegisterRequest request) {
		
		if(userRepository.existsByEmail(request.email())) {
			throw new EmailAlreadyExistsException();
		}

	}
	
	public List<UserDTO> getAllUsers(){
		
		return userRepository.findAll().stream()
				.map(user -> new UserDTO(
						user.getId(),
						user.getName(),
						user.getEmail(),
						user.getRA(),
						user.getRole().authority()
						))
				.collect(Collectors.toList());
		
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}
	
	public UserDTO toDTO(User user) {
		return new UserDTO(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getRA(),
				user.getRole().authority()
				);
	}
	
	public User fromDTO(UserDTO dto) {
		User user = new User();
		if(dto.ID() != null) {
			user.setId(dto.ID());
		}
		user.setName(dto.name());
		user.setEmail(dto.email());
		user.setRA(dto.RA());
		user.setRole(Role.valueOf(dto.role().substring(5)));
		
		return user;
	}
	
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		userRepository.delete(user);
	}
	
	
}
