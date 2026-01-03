package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name="posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private String description;
	
	private LocalDateTime createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "user_id", nullable=false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "topic_id", nullable=false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Topic topic;
	
	public Post() { }
	
	public Post(Integer id, String title, String description, LocalDateTime createdAt, User user, Topic topic) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.user = user;
		this.topic = topic;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
}
