package com.demo.verificationservicerest.service.model;

import lombok.Data;

@Data
public class VerifyPhoneRequestModel {
  private Long id;
  private String phoneNum;
}
