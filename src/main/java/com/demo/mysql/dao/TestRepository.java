package com.demo.mysql.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.demo.mysql.model.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

	public TestEntity getAllEmployeeById(Long id);

}
