package com.account.multidb.api.mysql.repository;

import com.account.multidb.api.mysql.entity.Account;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "mysqlAccountRepo")
public interface AccountRepository extends JpaRepository<Account, BigDecimal> {}
