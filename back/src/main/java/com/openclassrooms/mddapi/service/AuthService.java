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
	 Enregistre un nouvel utilisateur avec email unique et mot de passe encodé
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
	 Authentifie un utilisateur et retourne un JWT si les infos sont correctes
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
	
	/*
	 Met à jour les informations d'un utilisateur existant
	 */
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
	
	/*
	 Vérifie que le mot de passe respecte les critères de sécurité 
	 */
	private void validatePassword(String password) {
	    if (!password.matches(PASSWORD_REGEX)) {
	        throw new IllegalArgumentException(
	            "Le mot de passe doit contenir au moins 8 caractères, " +
	            "une majuscule, une minuscule, un chiffre et un caractère spécial"
	        );
	    }
	}
	
}
