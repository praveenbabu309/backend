package com.demo.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name ="user")
@Entity
@Data
@NoArgsConstructor
public class UserEntity {

	@Id
	@Column(name="username")
	private String userName;
	private String password;
	
	
}