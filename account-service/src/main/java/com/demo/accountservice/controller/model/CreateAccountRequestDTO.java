package com.demo.accountservice.controller.model;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CreateAccountRequestDTO {
  @NotEmpty
  @Size(min = 3, message = "First name is required.")
  private String firstName;

  @NotEmpty
  @Size(min = 3, message = "Last name is required.")
  private String lastName;

  @NotNull(message = "Date of Birth is required. Expected format: 2001-05-25")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dob;

  @NotEmpty(message = "Phone number is required.")
  @Size(min = 10, max = 10)
  private String phoneNum;
}
