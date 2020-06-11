package com.example.accountservicedemo.controller.model;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CreateAccountRequestDTO {
	@NotEmpty
	@Size(min = 3)
	private String firstName;
	
	@NotEmpty
	@Size(min = 3)
	private String lastName;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	
	@NotEmpty
	@Size(min = 10, max = 10)
	private String phoneNum;

}
