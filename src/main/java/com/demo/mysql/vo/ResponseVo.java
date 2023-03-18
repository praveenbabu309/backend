package com.demo.mysql.vo;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseVo <T> implements Serializable {
	
	private Long id;
	private String message;
	private HttpStatus status;
	
}