package com.demo.mysql.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mysql.Entity.TestEntity;
import com.demo.mysql.Repository.TestRepository;

@RestController
@RequestMapping("/api")
public class TestController {
	
	@Autowired
	private TestRepository testRepo;
	@GetMapping("/fetch")
	private List<TestEntity> fetch() {
		
		return testRepo.findAll();
	}

}
