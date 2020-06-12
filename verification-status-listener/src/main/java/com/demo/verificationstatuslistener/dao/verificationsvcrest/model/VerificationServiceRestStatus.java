package com.demo.verificationstatuslistener.dao.verificationsvcrest.model;

import lombok.Data;

@Data
public class VerificationServiceRestStatus {
  private Long id;
  private String status;
  private String verficationSystem;
}
