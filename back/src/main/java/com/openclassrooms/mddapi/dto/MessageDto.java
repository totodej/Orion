package com.openclassrooms.mddapi.dto;

public class MessageDto {
	private Integer userId;
	private Integer postId;
	private String message;
	
	public MessageDto() {}
	
	public MessageDto(Integer userId, Integer postId, String message) {
		this.userId = userId;
		this.postId = postId;
		this.message = message;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getPostId() {
		return postId;
	}

	public String getMessage() {
		return message;
	}
	
	
}
