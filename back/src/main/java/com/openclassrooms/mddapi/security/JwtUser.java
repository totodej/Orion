package com.openclassrooms.mddapi.security;


public class JwtUser {
	private Integer userId;
    private String name;
    private String email;
    private String createdAt;

    public JwtUser(Integer userId, String name, String email, String createdAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Integer getUserId() { 
    	return userId; 
    }
    
    public String getName() { 
    	return name; 
    }
    
    public String getEmail() { 
    	return email; 
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
}
