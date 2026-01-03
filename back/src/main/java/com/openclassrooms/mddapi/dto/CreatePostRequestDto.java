package com.openclassrooms.mddapi.dto;

public class CreatePostRequestDto {
	
	private String title;
	private String description;
	private Integer topicId;
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public Integer getTopicId() {
		return topicId;
	}
	
	

}
