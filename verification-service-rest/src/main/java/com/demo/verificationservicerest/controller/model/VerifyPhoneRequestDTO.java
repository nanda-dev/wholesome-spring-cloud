package com.demo.verificationservicerest.controller.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class VerifyPhoneRequestDTO {
	@NotNull
	private Long id;	
	
	
	@NotEmpty
	@Size(min = 10, max = 10)
	private String phoneNum;

}
