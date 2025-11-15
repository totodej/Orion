package com.openclassrooms.mddapi.dto;

public class UserDto {
	private Integer id;
	private String name;
    private String email;
    private String created_at;

    public UserDto(Integer id, String name, String email, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created_at = createdAt;
    }

	public Integer getId() {
        return id;
    }
    
    public String getName() {
    	return name;
    }

    public String getEmail() {
        return email;
    }
    
    public String getCreated_at() {
        return created_at;
    }

}
