package com.openclassrooms.mddapi.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.LoginRequestDto;
import com.openclassrooms.mddapi.dto.LoginResponseDto;
import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.UpdateUserRequestDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.security.JwtUser;
import com.openclassrooms.mddapi.security.JwtUtil;
import com.openclassrooms.mddapi.service.AuthService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;
	private final JwtUtil jwtUtil;
	
	public AuthController(AuthService authService, JwtUtil jwtUtil) {
		this.authService = authService;
		this.jwtUtil = jwtUtil;
	}
	
	/*
	 Enregistre un nouvel utilisateur et retourne immédiatement un token JWT.
	*/
	@PostMapping("/register")
	public ResponseEntity<LoginResponseDto> register(@RequestBody RegisterRequestDto request) {
		authService.register(request.getName(), request.getEmail(), request.getPassword());
		String token = authService.login(request.getEmail(), request.getPassword());
		return ResponseEntity.ok(new LoginResponseDto(token));
	}
	
	/*
	 Authentifie un utilisateur et renvoie un token JWT.
	*/
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
		try {
			String token = authService.login(request.getEmail(), request.getPassword());
			return ResponseEntity.ok(new LoginResponseDto(token));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "error"));
		}
	}
	
	@GetMapping("/me")
	public ResponseEntity<Object> me() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || "anonymousUser".equals(authentication.getPrincipal())) {
	        return ResponseEntity.ok(new Object());
	    }

	    Object principal = authentication.getPrincipal();
	    
	    if (!(principal instanceof JwtUser)) {
	        return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
	    }
	    
	    JwtUser user = (JwtUser) principal;

	    UserDto dto = new UserDto(
	        user.getUserId(),
	        user.getName(),
	        user.getEmail(),
	        user.getCreatedAt()
	    );

	    return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/me")
	public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequestDto request) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || "anonymousUser".equals(authentication.getPrincipal())) {
	        return ResponseEntity.ok(new Object());
	    }

	    Object principal = authentication.getPrincipal();
	    
	    if (!(principal instanceof JwtUser)) {
	        return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
	    }
	    
	    JwtUser user = (JwtUser) principal;
	    
	    UserDto updatedUser = authService.updateUser(
	    		user.getUserId(),
	    		request.getName(),
	    		request.getEmail(),
	    		request.getPassword());
	    
	    User entity = new User();
	    entity.setId(updatedUser.getId());
	    entity.setName(updatedUser.getName());
	    entity.setEmail(updatedUser.getEmail());
	    entity.setCreatedAt(LocalDateTime.now());
	    
	    String newToken = jwtUtil.generateToken(entity);
		
		return ResponseEntity.ok(Map.of("token", newToken, "user", updatedUser));
	}
	
}












