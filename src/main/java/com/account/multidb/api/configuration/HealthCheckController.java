package com.account.multidb.api.configuration;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealth;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.event.NonResponsiveConsumerEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthCheckController {

  @Autowired private MeterRegistry registry;
  @Autowired private HealthEndpoint healthEndpoint;

  private final List<Integer> mysqlConnection = new ArrayList<>();
  private final List<Integer> postgreSqlConnection = new ArrayList<>();
  private final List<Integer> kafkaConnection = new ArrayList<>();

  @GetMapping("/health")
  public String healthCheck() {
    Gauge.builder("mysql_connection", mysqlConnection, List::size).register(registry);
    Gauge.builder("pgsql_connection", postgreSqlConnection, List::size).register(registry);
    Gauge.builder("kafka_connection", kafkaConnection, List::size).register(registry);

    CompositeHealth compositeHealth = (CompositeHealth) healthEndpoint.health();
    CompositeHealth compositeHealth1 = (CompositeHealth) compositeHealth.getComponents().get("db");
    HealthComponent mysqlHealth = compositeHealth1.getComponents().get("mysqlDataSource");
    mysqlConnection.clear();
    if (mysqlHealth.getStatus().equals(Status.DOWN)) {
      log.info("MYSQL db connection down");
      mysqlConnection.add(1);
    } else {
      log.info("MYSQL db connection up");
    }

    HealthComponent postgresHealth = compositeHealth1.getComponents().get("postgresDataSource");
    postgreSqlConnection.clear();
    if (postgresHealth.getStatus().equals(Status.DOWN)) {
      log.info("Postgres db connection down");
      postgreSqlConnection.add(1);
    } else {
      log.info("Postgres db connection up");
    }
    return "status: " + healthEndpoint.health().getStatus();
  }

  @EventListener()
  public void eventHandler(NonResponsiveConsumerEvent event) {
    // When Kafka server is down, NonResponsiveConsumerEvent error is caught here.
    kafkaConnection.add(1);
    System.out.println("CAUGHT the event " + event);
  }
}
