package com.demo.mysql.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.mysql.Entity.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

}
