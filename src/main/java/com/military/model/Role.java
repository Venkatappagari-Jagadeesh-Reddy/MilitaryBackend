package com.military.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="roles")

public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true)
	private String name;
	 @Column(name = "created_date")
	private java.sql.Timestamp createdDate;
	 
	 @Column(name = "updated_date")
	private java.sql.Timestamp updatedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.sql.Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.sql.Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public java.sql.Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(java.sql.Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	 
	 
	

}
