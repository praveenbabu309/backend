package com.demo.mysql.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name ="test")
@Entity
@Data
@NoArgsConstructor
public class TestEntity {

	@Id
	@Column(name="id")
	private Long id;
	private String name;
}
