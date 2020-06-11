package com.example.accountservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.accountservicedemo.dao.model.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {

}
