package com.demo.verificationservicerest.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.verificationservicerest.controller.model.VerifyPhoneRequestDTO;
import com.demo.verificationservicerest.controller.model.VerifyPhoneResponseDTO;
import com.demo.verificationservicerest.service.VerificationService;
import com.demo.verificationservicerest.service.model.VerifyPhoneRequestModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/verify")
@Slf4j
public class VerificationController {
	private final ModelMapper mapper;
	private final VerificationService verificationService;

	public VerificationController(ModelMapper mapper, VerificationService verificationService) {
		super();
		this.mapper = mapper;
		this.verificationService = verificationService;
	}

	@PostMapping
	public VerifyPhoneResponseDTO createAccount(@RequestBody @Valid VerifyPhoneRequestDTO request) {
		log.info("Verify Phone Number. Request: [{}]", request);
		verificationService.verifyPhone(mapper.map(request, VerifyPhoneRequestModel.class));
		VerifyPhoneResponseDTO response = new VerifyPhoneResponseDTO();
		response.setStatus(true);
		return response;
	}

}
