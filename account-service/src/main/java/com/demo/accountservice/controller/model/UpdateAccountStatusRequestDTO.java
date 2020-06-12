package com.demo.accountservice.controller.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateAccountStatusRequestDTO {
  @NotNull(message = "Id is required.") private Long id;

  @NotEmpty(message = "Status is required.") private String status;

  @NotEmpty(message = "Source is required.") private String source;
}
