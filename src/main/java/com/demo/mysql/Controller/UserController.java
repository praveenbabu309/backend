package com.demo.mysql.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.mysql.vo.ResponseVo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.demo.mysql.dao.TestRepository;
import com.demo.mysql.model.TestEntity;
import com.demo.mysql.model.UserEntity;
import com.demo.mysql.service.Employeeservice;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")

public class UserController {

	
	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/login")
	public ResponseVo<Object> login(@RequestBody UserEntity user ) {
		if ("a".equals(user.getUserName()) && "b".equals(user.getPassword())) {
//			HttpSession newSession = request.getSession(true);
//			newSession.setAttribute("session", "abcdef1234");
		String token= empservice.generateToken( user.getUserName());
			return ResponseVo.builder().status(HttpStatus.OK).message(token).build();
		} else {
			return ResponseVo.builder().status(HttpStatus.BAD_REQUEST).message("fail").build();
		}
	}
	
	@PostMapping("/logout")
	public String login( HttpServletRequest request,
			HttpServletRequest response) {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("session") != null) {
			session.invalidate();
			return "Logout success";
		} else {
			return "invalid";
		}
	}
	

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
	
	@PostMapping("/csvdownload")
	public ResponseEntity<ResponseVo> csvdownload (@RequestBody boolean isCsvDownload){
		return empservice.csvdownload(isCsvDownload);
	}
	
	@PostMapping("/csvAdd")
	public ResponseEntity<ResponseVo> csvAdd(@RequestParam("file") MultipartFile file) throws IOException, CsvValidationException{
		if (!file.isEmpty()) {
	        try (InputStream inputStream = file.getInputStream()) {
	            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
	                String[] line;
	                int i=0;
	                while ((line = reader.readNext()) != null) {
	                	String a;
	                	for(int k=0 ;k<line.length;k++) {
	                		a=line[k];
	                	}
	                }
	                
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	           
	        }
	       
	}
		ResponseVo res = ResponseVo.builder().status(HttpStatus.OK).message("Success").build();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
		
	}

