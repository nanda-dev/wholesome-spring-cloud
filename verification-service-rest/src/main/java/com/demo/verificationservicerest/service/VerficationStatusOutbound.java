package com.demo.verificationservicerest.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface VerficationStatusOutbound {
  String OUTPUT = "verificationstatus";

  @Output(VerficationStatusOutbound.OUTPUT)
  MessageChannel output();
}
