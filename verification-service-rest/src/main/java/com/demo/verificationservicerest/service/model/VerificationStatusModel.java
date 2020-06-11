package com.demo.verificationservicerest.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VerificationStatusModel {
	private Long id;
	private String status;
	private String verficationSystem;
}
