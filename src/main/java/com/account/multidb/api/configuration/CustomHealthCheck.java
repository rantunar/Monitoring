package com.account.multidb.api.configuration;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomHealthCheck implements HealthIndicator {

  @Autowired private MeterRegistry registry;

  @Autowired
  @Qualifier(value = "mysqlDataSource")
  private DataSource mysqlDataSource;

  @Autowired
  @Qualifier(value = "postgresDataSource")
  private DataSource postgresDataSource;

  private final List<Integer> mysqlConnection = new ArrayList<>();
  private final List<Integer> postgreSqlConnection = new ArrayList<>();

  @Override
  public Health health() {
    Gauge.builder("mysql_connection", mysqlConnection, List::size).register(registry);
    Gauge.builder("pgsql_connection", postgreSqlConnection, List::size).register(registry);
    try {
      mysqlConnection.clear();
      Connection connection = mysqlDataSource.getConnection();
      connection.prepareStatement("select 1 from account").execute();
      log.info("MYSQL db health check completed");
    } catch (Exception e) {
      mysqlConnection.add(1);
      return Health.down().withDetail("MYSQL db connection error", e.getMessage()).build();
    }
    try {
      postgreSqlConnection.clear();
      Connection connection = postgresDataSource.getConnection();
      connection.prepareStatement("select 1 from account").execute();
      log.info("pgadmin db health check completed");
    } catch (Exception e) {
      postgreSqlConnection.add(1);
      return Health.down().withDetail("Postgresql db connection error", e.getMessage()).build();
    }
    return Health.up().build();
  }
}
