package com.demo.verificationservicerest.service;

import com.demo.verificationservicerest.service.model.VerificationStatusModel;

public interface NotificationService {

  void notifyStatus(VerificationStatusModel verificationStatus);
}
