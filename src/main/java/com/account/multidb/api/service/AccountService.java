package com.account.multidb.api.service;

import com.account.multidb.api.restmodel.Account;
import java.util.List;

public interface AccountService {

  public String createAccount(Account account);

  public List<Account> getAllAccounts();
}
