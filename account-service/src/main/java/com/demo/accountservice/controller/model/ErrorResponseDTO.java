package com.demo.accountservice.controller.model;

import java.time.ZonedDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
	private String message;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ'['VV']'")
	private ZonedDateTime timestamp;
	private String uri;
	private Map<String, String> details;	
	
}
