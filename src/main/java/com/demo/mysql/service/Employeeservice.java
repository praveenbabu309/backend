package com.demo.mysql.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.mysql.dao.TestRepository;
import com.demo.mysql.model.TestEntity;
import com.demo.mysql.vo.ResponseVo;

@Service
public class Employeeservice {
	@Autowired
	TestRepository empRepo;

	@SuppressWarnings("null")
	public ResponseEntity<String> bulkedit(List<TestEntity> employees) {

		try {
			for (TestEntity row : employees) {
				TestEntity emp = empRepo.getAllEmployeeById(row.getId());

				if (Objects.isNull(emp)) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found  " + row.getId());
				}
				emp.setName(row.getName());
				empRepo.save(emp);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated Successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found {}");
		}

	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity<ResponseVo> bulkadd(List<TestEntity> entity) {

		try {
			 ArrayList<Integer> nonduplicate = new ArrayList<>();
			for (TestEntity row : entity) {
				boolean contains=nonduplicate.contains(row.getId().intValue());
				if(!contains)
				{
					nonduplicate.add(row.getId().intValue());
				if (empRepo.existsById(row.getId())) {
					ResponseVo res = ResponseVo.builder().status(HttpStatus.CONFLICT).id(row.getId()).message(row.getId()+" is already presented")
							.build();
					return new ResponseEntity<>(res, HttpStatus.CONFLICT);
				}
				
			}
				else
				{
					ResponseVo res = ResponseVo.builder().status(HttpStatus.CONFLICT).id(row.getId()).message("Duplicate entry in given inputs")
							.build();
					return new ResponseEntity<>(res, HttpStatus.CONFLICT);
				}
			}
			for (TestEntity row : entity) {
				empRepo.save(row);

			}
			ResponseVo res = ResponseVo.builder().status(HttpStatus.OK).message("success").build();
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			ResponseVo res = ResponseVo.builder().status(HttpStatus.BAD_GATEWAY).message("fail").build();
			return new ResponseEntity<>(res, HttpStatus.BAD_GATEWAY);
		}
	}

	public ResponseEntity<ResponseVo> csvdownload(boolean isCSVlist) {
		StringJoiner joiner = new StringJoiner(", ");
		joiner.add("Id");
		joiner.add("Name");
		StringBuilder result = new StringBuilder();
		result.append(joiner);
		result.append(System.lineSeparator());
		if(isCSVlist) {
			
			List<TestEntity> emp= empRepo.findAll();
			for(TestEntity row:emp){
				joiner = new StringJoiner(", ");
				joiner.add(Long.toString(row.getId()));
				joiner.add(row.getName());
				result.append(joiner);
				result.append(System.lineSeparator());
			}
			
		}
		ResponseVo res = ResponseVo.builder().status(HttpStatus.OK).message(result.toString()).build();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	public String generateToken( String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 86450000);
        
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", "1")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor("abcdefghijklmnoprqssfijsbdfeeeeeeeeeeeeeeeeeeeeeeef".getBytes()))
                .compact();
	}
}
