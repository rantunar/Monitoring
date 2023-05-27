package com.account.multidb.api.service;

import com.account.multidb.api.handler.ApplicationException;
import com.account.multidb.api.postgres.repository.AccountRepository;
import com.account.multidb.api.restmodel.Account;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Autowired
  @Qualifier("mysqlAccountRepo")
  com.account.multidb.api.mysql.repository.AccountRepository mysqlAccountRepo;

  @Autowired
  @Qualifier("postgresRepository")
  AccountRepository postgresAccountRepo;

  @Override
  public String createAccount(final Account account) {
    try {
      if (account.getIsPrimary()) {
        mysqlAccountRepo.save(
            com.account.multidb.api.mysql.entity.Account.builder()
                .accountNumber(account.getAccountNumber())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .insertedAt(LocalDateTime.now())
                .build());
      } else {
        postgresAccountRepo.save(
            com.account.multidb.api.postgres.entity.Account.builder()
                .accountNumber(account.getAccountNumber())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .insertedAt(LocalDateTime.now())
                .build());
      }
      return String.format(
          "Account is created with account number = %s", account.getAccountNumber());
    } catch (final Exception e) {
      throw new ApplicationException(e.getMessage());
    }
  }

  @Override
  public List<Account> getAllAccounts() {
    try {
      final List<Account> accounts = new ArrayList<>();
      mysqlAccountRepo
          .findAll()
          .forEach(
              e ->
                  accounts.add(
                      new Account(e.getAccountNumber(), e.getFirstName(), e.getLastName(), true)));
      postgresAccountRepo
          .findAll()
          .forEach(
              e ->
                  accounts.add(
                      new Account(e.getAccountNumber(), e.getFirstName(), e.getLastName(), false)));
      return accounts;
    } catch (final Exception e) {
      throw new ApplicationException(e.getMessage());
    }
  }
}
