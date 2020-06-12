package com.demo.accountservice.dao.verificationsvcrest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.accountservice.dao.verificationsvcrest.model.VerifyPhoneRequest;
import com.demo.accountservice.dao.verificationsvcrest.model.VerifyPhoneResponse;

@FeignClient("verification-service-rest")
public interface VerificationServiceRestClient {
	@PostMapping("/verify")
	public VerifyPhoneResponse verifyPhone(@RequestBody VerifyPhoneRequest request);
}
