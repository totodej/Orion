package com.openclassrooms.mddapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.CreatePostRequestDto;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class PostService {
	private PostRepository postRepository;
	private UserRepository userRepository;
	private TopicRepository topicRepository;
	private SubscriptionRepository subscriptionRepository;
	
	public PostService(
			PostRepository postRepository, 
			UserRepository userRepository, 
			TopicRepository topicRepository, 
			SubscriptionRepository subscriptionRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
		this.subscriptionRepository = subscriptionRepository;
	}
	
	/*
	 Récupère les posts des topics auxquels un utilisateur est abonné, triés par date
	 */
	public List<Post> getPostsForUser(Integer userId, String sortDirection) {
		List<Integer> topicIds = subscriptionRepository.findByUserId(userId)
                .stream()
                .map(sub -> sub.getTopic().getId())
                .collect(Collectors.toList());
		
		if (topicIds.isEmpty()) {
            return List.of();
        }
		
		Sort sort = sortDirection.equalsIgnoreCase("asc")
		        ? Sort.by("createdAt").ascending()
		        : Sort.by("createdAt").descending();
		
		return postRepository.findByTopicIdIn(topicIds, sort);
	}
	
	/*
	 Crée un nouveau post pour un utilisateur et un topic donnés 
	 */
	public Post createPost(CreatePostRequestDto req, Integer userId) {
		LocalDateTime currentDate = LocalDateTime.now();
		
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
		
		Topic topic = topicRepository.findById(req.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));
		
		Post post = new Post();
		post.setTitle(req.getTitle());
		post.setDescription(req.getDescription());
		post.setCreatedAt(currentDate);
		post.setUser(user);
		post.setTopic(topic);
		
		return postRepository.save(post);
	}
	
	/*
	 Récupère tous les posts d'un topic donné
	 */
	public List<Post> getByTopic(Integer topicId) {
		return postRepository.findByTopicId(topicId);
	}
	
	/*
	 Récupère un post par son ID
	 */
	public Post getPostById(Integer id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Post not found"));
	}
}
