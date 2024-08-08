package com.maverick.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {
	
	@Id
//	Below is commented because we will add only few roles in DB with ID defined in AppConstants
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;

}
