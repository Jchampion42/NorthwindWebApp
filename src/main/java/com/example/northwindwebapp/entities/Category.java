package com.example.northwindwebapp.entities;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@Column(name = "CategoryID", nullable = false)
	private Integer id;

	@Column(name = "CategoryName", nullable = false, length = 15)
	private String categoryName;

	@Lob
	@Column(name = "Description")
	private String description;

	@Column(name = "Picture")
	private byte[] picture;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

}