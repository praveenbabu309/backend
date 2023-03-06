package com.demo.mysql.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.mysql.dao.TestRepository;
import com.demo.mysql.model.TestEntity;

@Service
public class Employeeservice {
	@Autowired
	TestRepository empRepo;
	
	
	@SuppressWarnings("null")
	public ResponseEntity<String> bulkedit(List<TestEntity>employees) 
	{
		try {
			for(TestEntity row: employees){
			TestEntity  emp=empRepo.getAllEmployeeById(row.getId());
			
			if(Objects.isNull(emp)) {
				return ResponseEntity
		        .status(HttpStatus.NOT_FOUND)
		        .body("Employee not found  "+row.getId());
			}
			emp.setName(row.getName());
			empRepo.save(emp);
		}
		return ResponseEntity
		        .status(HttpStatus.ACCEPTED)
		        .body("Updated Successfully");
		}
		catch(Exception e){
			return ResponseEntity
			        .status(HttpStatus.NOT_FOUND)
			        .body("Employee not found {}");
		}
		
	}
}


