package com.demo.accountservice.dao.verificationsvcrest.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VerifyPhoneRequest {
  private Long id;
  private String phoneNum;
}
