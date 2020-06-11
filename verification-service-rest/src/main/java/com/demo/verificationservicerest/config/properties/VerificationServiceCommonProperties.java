package com.demo.verificationservicerest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "verify-phone")
public class VerificationServiceCommonProperties {
  private Integer sleepDurationInSeconds;
}
