package com.demo.accountservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.accountservice.dao.model.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {
	boolean existsByPhoneNum(String phoneNum);
}
