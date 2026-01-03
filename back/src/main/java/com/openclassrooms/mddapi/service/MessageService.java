package com.openclassrooms.mddapi.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Message;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.MessageRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class MessageService {
	private final MessageRepository messageRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	
	public MessageService(MessageRepository messageRepository, UserRepository userRepository, PostRepository postRepository) {
		this.messageRepository = messageRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	/*
	 Crée un nouveau message pour un utilisateur et un post donnés
	 */
	public Message createMessage(Integer userId, Integer postId, String content) {
		LocalDateTime date = LocalDateTime.now();
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		
		Message message = new Message();
		message.setUser(user);
		message.setPost(post);
		message.setMessage(content);
		message.setCreatedAt(date);
		
		return messageRepository.save(message);
	}
	
	/*
	 Récupère tous les messages d'un post triés par date croissante
	 */
	public List<Message> getMessageByPost(Integer postId) {
		return messageRepository.findByPostIdOrderByCreatedAtAsc(postId);
	}

}
