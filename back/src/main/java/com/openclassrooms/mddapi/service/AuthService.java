package com.openclassrooms.mddapi.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtUtil;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private static final String PASSWORD_REGEX =
		    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}
	
	/*
	 Enregistre un nouvel utilisateur dans la base de données.
	 Vérifie que l'email n'est pas déjà utilisé et encode le mot de passe.
	*/
	public User register(String name, String email, String password) {
		if(userRepository.existsByEmail(email)) {
			throw new RuntimeException("Email is already in use");
		}
		
		validatePassword(password);
		
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setCreatedAt(LocalDateTime.now());
		
		return userRepository.save(user);
	}
	
	/*
	 Authentifie un utilisateur avec email et mot de passe.
	 Si les informations sont correctes, génère un JWT.
	*/
	public String login(String identifier, String password) {
		Optional<User> optionalUser = userRepository.findByEmailOrName(identifier, identifier);
		
		User user = optionalUser
				.orElseThrow(()-> new ResourceNotFoundException("User not found"));
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Incorrect password");
		}
		
		String token = jwtUtil.generateToken(user);
		
		return token;
	}
	
	public UserDto updateUser(Integer userId, String name, String email, String password) {
		User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		if (name != null && !name.isEmpty()) {
	        user.setName(name);
	    }

	    if (email != null && !email.isEmpty()) {
	        user.setEmail(email);
	    }

	    if (password != null && !password.isEmpty()) {
	        user.setPassword(passwordEncoder.encode(password));
	    }

	    userRepository.save(user);
		
		return new UserDto(
	        user.getId(),
	        user.getName(),
	        user.getEmail(),
	        user.getCreatedAt().toString()
	    );
	}
	
	private void validatePassword(String password) {
	    if (!password.matches(PASSWORD_REGEX)) {
	        throw new IllegalArgumentException(
	            "Le mot de passe doit contenir au moins 8 caractères, " +
	            "une majuscule, une minuscule, un chiffre et un caractère spécial"
	        );
	    }
	}
	
}
