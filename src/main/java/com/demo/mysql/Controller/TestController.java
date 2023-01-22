package com.demo.mysql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.demo.mysql.dao.TestRepository;
import com.demo.mysql.model.TestEntity;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TestController {
	
	@Autowired
	private TestRepository testRepo;
		
	@GetMapping("/employees")
	public List<TestEntity> get() {
		return testRepo.findAll();
	}
	
	@PostMapping("/employees")
	public TestEntity createEmp(@RequestBody TestEntity entity) {
		
		return testRepo.save(entity);
	}
	
}

