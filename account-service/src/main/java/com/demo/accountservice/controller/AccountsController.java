package com.demo.accountservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.accountservice.controller.model.AccountResponseDTO;
import com.demo.accountservice.controller.model.CreateAccountRequestDTO;
import com.demo.accountservice.controller.model.UpdateAccountStatusRequestDTO;
import com.demo.accountservice.service.AccountsService;
import com.demo.accountservice.service.model.AccountModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountsController {
	private final ModelMapper mapper;
	private final AccountsService accountsService;

	public AccountsController(ModelMapper mapper, AccountsService accountsService) {
		super();
		this.mapper = mapper;
		this.accountsService = accountsService;
	}

	@PostMapping
	public AccountResponseDTO createAccount(@RequestBody @Valid CreateAccountRequestDTO account) {
		log.info("Create New Account. Request: [{}]", account);
		AccountModel newAccount = accountsService.createAccount(mapper.map(account, AccountModel.class));
		return mapper.map(newAccount, AccountResponseDTO.class);
	}

	@GetMapping
	public List<AccountResponseDTO> getAllAccounts() {
		log.info("Fetching all Accounts.");
		return accountsService.getAllAccounts().stream()
				.map(accountModel -> mapper.map(accountModel, AccountResponseDTO.class))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public AccountResponseDTO getAccountById(@PathVariable Long id) {
		log.info("Get Account by id: [{}]", id);
		return mapper.map(accountsService.getAccountById(id), AccountResponseDTO.class);
	}
	
	@PutMapping
	public AccountResponseDTO updateAccountStatus(@RequestBody @Valid UpdateAccountStatusRequestDTO updateRequest) {
		log.info("Update Account Status. Request: [{}]", updateRequest);
		AccountModel updatedAccount = accountsService.updateAccountStatus(mapper.map(updateRequest, AccountModel.class), updateRequest.getSource());
		return mapper.map(updatedAccount, AccountResponseDTO.class);
	}

}
