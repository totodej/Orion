package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;

@Entity
@Table(name="topics")
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	public Topic() {
		
	}
	
	public Topic(Integer id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
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

}
