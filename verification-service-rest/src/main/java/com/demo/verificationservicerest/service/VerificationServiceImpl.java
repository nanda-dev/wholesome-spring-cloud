package com.demo.verificationservicerest.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.demo.verificationservicerest.config.properties.VerificationServiceCommonProperties;
import com.demo.verificationservicerest.constants.Constants;
import com.demo.verificationservicerest.service.model.VerificationStatusModel;
import com.demo.verificationservicerest.service.model.VerifyPhoneRequestModel;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableConfigurationProperties({VerificationServiceCommonProperties.class})
public class VerificationServiceImpl implements VerificationService {
  private final NotificationService queueNotifier;
  private final VerificationServiceCommonProperties config;

  public VerificationServiceImpl(
      NotificationService queueNotifier, VerificationServiceCommonProperties config) {
    super();
    this.queueNotifier = queueNotifier;
    this.config = config;
  }

  @Override
  @Async
  public void verifyPhone(VerifyPhoneRequestModel verificationRequest) {
    try {
      log.info("Sleeping for [{}] seconds...zzz...", config.getSleepDurationInSeconds());
      TimeUnit.SECONDS.sleep(config.getSleepDurationInSeconds());
    } catch (InterruptedException ie) {
      log.info("Waking up!");
      Thread.currentThread().interrupt();
    }

    VerificationStatusModel verificationStatus = new VerificationStatusModel();
    verificationStatus
        .setId(verificationRequest.getId())
        .setStatus(getRandomVerificationStatus())
        .setVerficationSystem(Constants.VER_SYS);
    queueNotifier.notifyStatus(verificationStatus);
  }

  private String getRandomVerificationStatus() {
    List<String> statuses =
        Arrays.asList(Constants.VER_STATUS_SUCCESS, Constants.VER_STATUS_FAILURE);
    int index = new Random().ints(1, 0, 2).findFirst().getAsInt();
    return statuses.get(index);
  }
}
