package com.openclassrooms.mddapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class TopicService {
	@Autowired
	private TopicRepository topicRepository;
	
	public TopicService(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}
	
	/*
	 Récupère tous les topics existants
	 */
	public List<Topic> getAllTopics() {
		return topicRepository.findAll();
	}
}
