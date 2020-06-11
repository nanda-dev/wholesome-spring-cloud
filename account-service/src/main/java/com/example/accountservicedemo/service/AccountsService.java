package com.example.accountservicedemo.service;

import java.util.List;

import com.example.accountservicedemo.service.model.AccountModel;

public interface AccountsService {
	AccountModel createAccount(AccountModel account);
	AccountModel getAccountById(Long id);
	List<AccountModel> getAllAccounts();
	AccountModel updateAccountStatus(AccountModel account, String sourceSystem);
}
