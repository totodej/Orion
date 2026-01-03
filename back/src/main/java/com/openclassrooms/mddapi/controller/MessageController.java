package com.openclassrooms.mddapi.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.CreateMessageDto;
import com.openclassrooms.mddapi.model.Message;
import com.openclassrooms.mddapi.security.JwtUtil;
import com.openclassrooms.mddapi.service.MessageService;

@RestController
@RequestMapping("/api/posts/{postId}/messages")
public class MessageController {
	private final MessageService messageService;
	private final JwtUtil jwtUtil;
	
	public MessageController(MessageService messageService, JwtUtil jwtUtil) {
		this.messageService = messageService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping
	public ResponseEntity<?> addMessage(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable Integer postId,
			@RequestBody CreateMessageDto dto) {
		Integer userId = jwtUtil.extractUserId(authHeader.substring(7));
		
		Message saved = messageService.createMessage(
				userId,
				postId,
				dto.getMessage());
		
		return ResponseEntity.ok(saved);
	}
	
	@GetMapping
	public ResponseEntity<List<Message>> getMessages(@PathVariable Integer postId) {
		return ResponseEntity.ok(messageService.getMessageByPost(postId));
	}

}
