package com.openclassrooms.mddapi.service;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserDto getUserById(Integer id) {
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String createdAtStr = user.getCreatedAt() != null ? user.getCreatedAt().format(formatter) : null;
		
		UserDto userDto = new UserDto(
				user.getId(), 
				user.getName(), 
				user.getEmail(), 
				createdAtStr);
				
		return userDto;
	}
}
