package com.openclassrooms.mddapi.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
	private TopicService topicService;
	
	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}
	
	@GetMapping
	public ResponseEntity<List<Topic>> getTopics() {
		return ResponseEntity.ok(topicService.getAllTopics());
	}
}
