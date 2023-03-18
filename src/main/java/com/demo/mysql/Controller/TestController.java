package com.demo.mysql.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.demo.mysql.dao.TestRepository;
import com.demo.mysql.model.TestEntity;
import com.demo.mysql.service.Employeeservice;
import com.demo.mysql.vo.ResponseVo;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TestController {
	
	@Autowired
	private TestRepository testRepo;
	@Autowired
	private Employeeservice empservice;
		
	@GetMapping("/employees")
	public List<TestEntity> get() {
		return testRepo.findAll();
	}
	
	@PostMapping("/employees")
	public ResponseEntity<ResponseVo> createEmp(@RequestBody TestEntity entity) {
		
		// testRepo.save(entity);
		 if(testRepo.existsById(entity.getId())) {
			 ResponseVo res=ResponseVo.builder().status(HttpStatus.CONFLICT).id(entity.getId()).message("fail").build();
		 return new ResponseEntity<>(
		         res, 
		          HttpStatus.CONFLICT);
			
		 
		 
		 }
		 else {
			 testRepo.save(entity);
			 ResponseVo res=ResponseVo.builder().status(HttpStatus.OK).id(entity.getId()).message("success").build();
			 return new ResponseEntity<>(
			         res, 
			          HttpStatus.OK);
		 }
		 }
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<TestEntity> getEmployeeById( @PathVariable Long id) {
TestEntity emp=testRepo.getAllEmployeeById(id);
		return ResponseEntity.ok(emp);
	}
	
	@PostMapping("/updateemployees/{id}")
	public ResponseEntity<TestEntity> updateEmployee(@PathVariable Long id ,@RequestBody TestEntity entity){
		TestEntity emp=testRepo.getAllEmployeeById( id);
		emp.setId(entity.getId());
		emp.setName(entity.getName());
		TestEntity test =testRepo.save(emp);
		return ResponseEntity.ok(test);
		
	}

	@PostMapping("/deleteemployee/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		TestEntity emp = testRepo.getAllEmployeeById(id);
		testRepo.delete(emp);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted Successfully", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/deletemultipleemployee")
	public ResponseEntity<String> bulkDeleteEmployee(@RequestBody List<Long>id) {
		id.forEach(ids->{
			if(testRepo.existsById(ids)){
		testRepo.deleteById(ids);}
		});
		return ResponseEntity
		        .status(HttpStatus.ACCEPTED)
		        .body("Deleted Successfully");
	}
	
	@PostMapping("/bulkedit")
	public ResponseEntity<String> bulkedit(@RequestBody List<TestEntity>id){
		return empservice.bulkedit(id);
	}
	
	@PostMapping("/bulkadd")
	public ResponseEntity<ResponseVo> bulkadd (@RequestBody List<TestEntity> entity){
		return empservice.bulkadd(entity);
	}
	
}

