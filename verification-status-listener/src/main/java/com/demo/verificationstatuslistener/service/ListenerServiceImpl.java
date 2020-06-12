package com.demo.verificationstatuslistener.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.demo.verificationstatuslistener.constants.Constants;
import com.demo.verificationstatuslistener.dao.accountsvc.AccountServiceClient;
import com.demo.verificationstatuslistener.dao.accountsvc.model.UpdateAccountStatusRequest;
import com.demo.verificationstatuslistener.dao.accountsvc.model.UpdateAccountStatusResponse;
import com.demo.verificationstatuslistener.dao.verificationsvcrest.model.VerificationServiceRestStatus;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ListenerServiceImpl implements ListenerService {
  private final ModelMapper mapper;
  private final AccountServiceClient accountServiceClient;

  public ListenerServiceImpl(ModelMapper mapper, AccountServiceClient accountServiceClient) {
    super();
    this.mapper = mapper;
    this.accountServiceClient = accountServiceClient;
  }

  @Override
  public UpdateAccountStatusResponse updateAccountStatus(VerificationServiceRestStatus verificationStatus) {
    log.info("Processing Verification Status Message received.");
    UpdateAccountStatusRequest updateRequest = mapper.map(verificationStatus, UpdateAccountStatusRequest.class);
    updateRequest.setSource(Constants.VER_STATUS_LISTENER_SERVICE);
    return accountServiceClient.updateAccountStatus(updateRequest);
  }
}
