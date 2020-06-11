package com.demo.accountservice.controller.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateAccountStatusRequestDTO {
	@NotNull
	private Long id;
	
	@NotEmpty
	private String status;
	
	@NotEmpty
	private String source;
	
}
