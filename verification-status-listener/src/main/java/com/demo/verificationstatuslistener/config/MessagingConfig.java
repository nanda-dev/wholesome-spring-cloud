package com.demo.verificationstatuslistener.config;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.verificationstatuslistener.dao.accountsvc.model.UpdateAccountStatusResponse;
import com.demo.verificationstatuslistener.dao.verificationsvcrest.model.VerificationServiceRestStatus;
import com.demo.verificationstatuslistener.service.ListenerService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MessagingConfig {
  private final ListenerService listenerService;

  public MessagingConfig(ListenerService listenerService) {
    super();
    this.listenerService = listenerService;
  }

  @Bean
  public Consumer<VerificationServiceRestStatus> receive() {
    return payload -> {
      log.info("Verification Status Listener received payload: [{}]", payload);
      UpdateAccountStatusResponse response = listenerService.updateAccountStatus(payload);
      log.info("Verification Status Listener payload processing status: [{}]", response);      
    };
  }
}
