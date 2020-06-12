package com.demo.verificationstatuslistener.dao.accountsvc.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UpdateAccountStatusResponse {
  private Long id;
  private String firstName;
  private String lastName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dob;

  private String phoneNum;
  private String status;
}
