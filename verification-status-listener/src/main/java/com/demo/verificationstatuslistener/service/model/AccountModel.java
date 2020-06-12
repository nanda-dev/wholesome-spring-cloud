package com.demo.verificationstatuslistener.service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AccountModel {
  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate dob;
  private String phoneNum;
  private String status;
  private String createdBy;
  private LocalDateTime createdOn;
  private String modifiedBy;
  private LocalDateTime modifiedOn;
}
