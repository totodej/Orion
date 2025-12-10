package com.openclassrooms.mddapi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{

	boolean existsByUserIdAndTopicId(Integer userId, Integer topicId);
	
	void deleteByUserIdAndTopicId(Integer userId, Integer topicId);
	
	List<Subscription> findByUserId(Integer userId);
}
