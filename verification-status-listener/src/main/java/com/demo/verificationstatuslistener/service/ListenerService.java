package com.demo.verificationstatuslistener.service;

import org.springframework.stereotype.Service;

import com.demo.verificationstatuslistener.dao.accountsvc.model.UpdateAccountStatusResponse;
import com.demo.verificationstatuslistener.dao.verificationsvcrest.model.VerificationServiceRestStatus;

@Service
public interface ListenerService {
  UpdateAccountStatusResponse updateAccountStatus(VerificationServiceRestStatus verificationStatus);
}
