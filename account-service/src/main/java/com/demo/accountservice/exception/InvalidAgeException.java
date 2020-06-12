package com.demo.accountservice.exception;

public class InvalidAgeException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  static final String MESSAGE = "Age is not valid: ";

  public InvalidAgeException(Integer age) {
    super(MESSAGE + age);
  }
}
