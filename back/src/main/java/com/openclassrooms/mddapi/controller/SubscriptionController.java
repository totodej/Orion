package com.openclassrooms.mddapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.security.JwtUtil;
import com.openclassrooms.mddapi.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
	private final SubscriptionService subscriptionService;
	private final JwtUtil jwtUtil;
	
	public SubscriptionController(SubscriptionService subscriptionService, JwtUtil jwtUtil) {
		this.subscriptionService = subscriptionService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/{topicId}")
	public ResponseEntity<?> subscribeToTopic(
			@RequestHeader("Authorization") String authHeader, 
			@PathVariable Integer topicId
	) {
		Integer userId = jwtUtil.extractUserId(authHeader.substring(7));
		subscriptionService.subscribe(userId, topicId);
		return ResponseEntity.ok(Map.of("message", "Subscribed"));
	}
	
	@DeleteMapping("/{topicId}")
	public ResponseEntity<?> unsubscribeFromTopic(
			@RequestHeader("Authorization") String authHeader, 
			@PathVariable Integer topicId	
	) {
		Integer userId = jwtUtil.extractUserId(authHeader.substring(7));
		subscriptionService.unsubscribe(userId, topicId);
		return ResponseEntity.ok(Map.of("message", "Unsubscribed"));
	}
	
	@GetMapping("")
	public ResponseEntity<?> getMySubscriptions(
			@RequestHeader("Authorization") String authHeader
			) {
		Integer userId = jwtUtil.extractUserId(authHeader.substring(7));
		List<Subscription> subs = subscriptionService.getSubscriptionsByUser(userId);
		
		List<Map<String, Object>> result = subs.stream()
			    .map(s -> {
			        Map<String, Object> m = new HashMap<>();
			        m.put("id", s.getId());
			        m.put("topicId", s.getTopic().getId());
			        return m;
			    })
			    .collect(Collectors.toList());
		
		return ResponseEntity.ok(result);
	}
}
