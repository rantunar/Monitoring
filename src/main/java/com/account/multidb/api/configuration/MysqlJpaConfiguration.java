package com.account.multidb.api.configuration;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.account.multidb.api.mysql.repository"},
    entityManagerFactoryRef = "mysqlEntityManagerFactory",
    transactionManagerRef = "mysqlTransactionManager")
public class MysqlJpaConfiguration {

  @Bean
  @ConfigurationProperties("spring.datasource.mysql")
  public DataSourceProperties mysqlDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "mysqlDataSource")
  @Primary
  public DataSource mysqlDataSource() {
    return mysqlDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(mysqlDataSource())
        .packages("com.account.multidb.api.mysql.entity")
        .build();
  }

  @Bean
  public PlatformTransactionManager mysqlTransactionManager(
      @Qualifier("mysqlEntityManagerFactory")
          LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory) {
    return new JpaTransactionManager(Objects.requireNonNull(mysqlEntityManagerFactory.getObject()));
  }
}
