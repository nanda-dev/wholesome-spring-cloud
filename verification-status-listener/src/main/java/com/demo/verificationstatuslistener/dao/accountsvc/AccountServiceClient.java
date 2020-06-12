package com.demo.verificationstatuslistener.dao.accountsvc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.verificationstatuslistener.dao.accountsvc.model.UpdateAccountStatusRequest;
import com.demo.verificationstatuslistener.dao.accountsvc.model.UpdateAccountStatusResponse;

@FeignClient("accounts-service/accounts")
public interface AccountServiceClient {
	@PutMapping
	public UpdateAccountStatusResponse updateAccountStatus(@RequestBody UpdateAccountStatusRequest request);
	
}
