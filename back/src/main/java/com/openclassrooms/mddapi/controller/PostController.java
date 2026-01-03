package com.openclassrooms.mddapi.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.CreatePostRequestDto;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.security.JwtUtil;
import com.openclassrooms.mddapi.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	private PostService postService;
	private JwtUtil jwtUtil;
	
	public PostController(PostService postService, JwtUtil jwtUtil) {
		this.postService = postService;
		this.jwtUtil = jwtUtil;
	}
	
	/*
	 Crée un nouveau post pour l'utilisateur authentifié
	 */
	@PostMapping("")
	public ResponseEntity<?> createPost(
			@RequestHeader("Authorization") String authHeader, 
			@RequestBody CreatePostRequestDto req) 
	{
		Integer userId = jwtUtil.extractUserId(authHeader.substring(7));
		
		Post created = postService.createPost(req, userId);
		
		return ResponseEntity.ok(created);
	}
	
	/*
	 Récupère tous les posts des topics auxquels l'utilisateur est abonné, triés par date
	 */
	@GetMapping("")
	public ResponseEntity<?> getPostsForMySubscriptions(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "desc") String sort
    ) {
        Integer userId = jwtUtil.extractUserId(authHeader.substring(7));

        List<Post> posts = postService.getPostsForUser(userId, sort);

        return ResponseEntity.ok(posts);
    }
	
	/*
	 Récupère un post par son ID 
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

}
