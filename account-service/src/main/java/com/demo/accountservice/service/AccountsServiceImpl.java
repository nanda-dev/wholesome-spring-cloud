package com.demo.accountservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.demo.accountservice.config.properties.CreateAccountValidationProperties;
import com.demo.accountservice.constants.Constants;
import com.demo.accountservice.dao.AccountsRepository;
import com.demo.accountservice.dao.model.Account;
import com.demo.accountservice.dao.verificationsvcrest.VerificationServiceRestClient;
import com.demo.accountservice.dao.verificationsvcrest.model.VerifyPhoneRequest;
import com.demo.accountservice.exception.DuplicatePhoneNumberException;
import com.demo.accountservice.exception.InvalidAgeException;
import com.demo.accountservice.service.model.AccountModel;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableConfigurationProperties({CreateAccountValidationProperties.class})
public class AccountsServiceImpl implements AccountsService {
  private final ModelMapper mapper;
  private final AccountsRepository accountsRepo;
  private final CreateAccountValidationProperties createAccountValidationProperties;
  private final VerificationServiceRestClient verificationSvcClient;

  public AccountsServiceImpl(
      ModelMapper mapper,
      AccountsRepository accountsRepo,
      CreateAccountValidationProperties createAccountValidationProperties,
      VerificationServiceRestClient verificationSvcClient) {
    super();
    this.mapper = mapper;
    this.accountsRepo = accountsRepo;
    this.createAccountValidationProperties = createAccountValidationProperties;
    this.verificationSvcClient = verificationSvcClient;
  }

  @Override
  public AccountModel createAccount(AccountModel accountModel) {
    // Calculate Age
    Integer age = Period.between(accountModel.getDob(), LocalDate.now().plusDays(1)).getYears();

    log.info(
        "Checking whether Age [{}] is less than minimum age accepted by the system: [{}].",
        age,
        createAccountValidationProperties.getMinAge());

    if (age < createAccountValidationProperties.getMinAge()) {
      throw new InvalidAgeException(age);
    }
    
    // Check if another record exists with the same phone-number
    if(accountsRepo.existsByPhoneNum(accountModel.getPhoneNum())) {
    	throw new DuplicatePhoneNumberException(accountModel.getPhoneNum());
    }

    // Insert record into the Database
    accountModel.setCreatedBy(Constants.ACCOUNTS_SERVICE);
    accountModel.setCreatedOn(LocalDateTime.now());
    Account newAccount = accountsRepo.save(mapper.map(accountModel, Account.class));

    log.info("New Account created in database. Id: [{}]", newAccount.getId());

    // Call Verification Service to initiate Verification process.
    // Verification Service is an async process which will notify
    // the status upon its completion through RabbitMQ.

    VerifyPhoneRequest verificationRequest = new VerifyPhoneRequest();
    verificationRequest.setId(newAccount.getId()).setPhoneNum(newAccount.getPhoneNum());
    verificationSvcClient.verifyPhone(verificationRequest);

    log.info("Verification process initiated.");

    return mapper.map(newAccount, AccountModel.class);
  }

  @Override
  public AccountModel getAccountById(Long id) {
    Optional<Account> account = accountsRepo.findById(id);
    return account.isPresent() ? mapper.map(account.get(), AccountModel.class) : null;
  }

  @Override
  public List<AccountModel> getAllAccounts() {
    List<Account> accounts = accountsRepo.findAll();
    return accounts
        .stream()
        .map(account -> mapper.map(account, AccountModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public AccountModel updateAccountStatus(AccountModel accountModel, String sourceSystem) {
    Optional<Account> account = accountsRepo.findById(accountModel.getId());
    if (!account.isPresent()) {
      throw new RuntimeException("Account not found. id: " + accountModel.getId());
    }
    Account accountRecord = account.get();
    accountRecord.setStatus(accountModel.getStatus());
    accountRecord.setModifiedBy(sourceSystem);
    accountRecord.setModifiedOn(LocalDateTime.now());

    return mapper.map(accountsRepo.save(accountRecord), AccountModel.class);
  }
}
