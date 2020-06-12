package com.demo.verificationstatuslistener.dao.accountsvc.model;

import lombok.Data;

@Data
public class UpdateAccountStatusRequest {
  private Long id;
  private String status;
  private String source;
}
