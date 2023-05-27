package com.account.multidb.api.postgres.repository;

import com.account.multidb.api.postgres.entity.Account;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "postgresRepository")
public interface AccountRepository extends JpaRepository<Account, BigDecimal> {}
