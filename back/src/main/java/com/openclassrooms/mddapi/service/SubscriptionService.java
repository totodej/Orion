package com.openclassrooms.mddapi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class SubscriptionService {
	private SubscriptionRepository subscriptionRepository;
	private UserRepository userRepository;
	private TopicRepository topicRepository;
	
	public SubscriptionService(
			SubscriptionRepository subscriptionRepository, 
			UserRepository userRepository, 
			TopicRepository topicRepository
	) {
		this.subscriptionRepository = subscriptionRepository;
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
	}
	
	/*
	 Abonne un utilisateur à un topic si ce n'est pas déjà fait
	 */
	public void subscribe(Integer userId, Integer topicId) {
		
		if(subscriptionRepository.existsByUserIdAndTopicId(userId, topicId)) {
			return;
		}
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new RuntimeException("Topic not found"));
		
		subscriptionRepository.save(new Subscription(user, topic));
	}
	
	/*
	 Désabonne un utilisateur d'un topic
	 */
	@Transactional
	public void unsubscribe(Integer userId, Integer topicId) {
		subscriptionRepository.deleteByUserIdAndTopicId(userId, topicId);
	}
	
	/*
	 Récupère toutes les abonnements d'un utilisateur
	 */
	public List<Subscription> getSubscriptionsByUser(Integer userId) {
        return subscriptionRepository.findByUserId(userId);
    }

}
