package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
// THIS IS THE USER DATA TRANSFER OBJECT WHICH WILL BE MAPPED TO TABLE AS A PART OF ORM (OBJECT RELATIONAL MAPPING)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TABLE")
public class User implements Serializable {
      

	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String password;
	private String email;

	
	
	

}
