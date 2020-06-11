package com.demo.verificationservicerest.service;

import com.demo.verificationservicerest.service.model.VerifyPhoneRequestModel;

public interface VerificationService {
	
	void verifyPhone(VerifyPhoneRequestModel verificationRequest);
	
}
