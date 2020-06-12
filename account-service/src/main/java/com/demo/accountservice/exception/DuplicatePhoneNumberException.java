package com.demo.accountservice.exception;

public class DuplicatePhoneNumberException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  static final String MESSAGE = "Phone number already exists in the system: ";

  public DuplicatePhoneNumberException(String phoneNum) {
    super(MESSAGE + phoneNum);
  }
}
