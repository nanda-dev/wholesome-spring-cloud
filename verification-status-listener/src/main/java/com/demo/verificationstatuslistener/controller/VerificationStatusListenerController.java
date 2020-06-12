package com.demo.verificationstatuslistener.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.verificationstatuslistener.service.ListenerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/listener")
@Slf4j
public class VerificationStatusListenerController {
  private final ModelMapper mapper;
  private final ListenerService accountsService;

  public VerificationStatusListenerController(ModelMapper mapper, ListenerService accountsService) {
    super();
    this.mapper = mapper;
    this.accountsService = accountsService;
  }
  

  @GetMapping
  public String demo() {
    log.info("Listener root invoked");
    return "LISTENER_UP";
  }

  
}
