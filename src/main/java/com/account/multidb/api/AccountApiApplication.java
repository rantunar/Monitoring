package com.account.multidb.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.account.multidb.api"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AccountApiApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        // .beanNameGenerator(new UniqueNameGenerator())
        .sources(AccountApiApplication.class)
        .run();
  }
}
