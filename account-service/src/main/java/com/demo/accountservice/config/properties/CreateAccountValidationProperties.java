package com.demo.accountservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "create-account")
public class CreateAccountValidationProperties {
  private Integer minAge;
}
