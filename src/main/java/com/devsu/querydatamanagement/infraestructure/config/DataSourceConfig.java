package com.devsu.querydatamanagement.infraestructure.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;

@Profile("!test")
@Configuration
@EnableJpaRepositories(
        basePackages = {"com.devsu.querydatamanagement.infraestructure.dbcustomeradapter"},
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "primaryTransactionManager"
)
public class DataSourceConfig {

    @Value("${data.management.datasource.url}")
    private String urlDatabase;

    @Value("${data.management.datasource.username}")
    private String usernameDatabase;

    @Value("${data.management.datasource.password}")
    private String passwordDatabase;

    @Value("${data.management.datasource.driver.class.name}")
    private String driverClassName;

    @Value("${data.management.datasource.database.platform}")
    private String databasePlatform;

    @Value("${data.management.datasource.schema}")
    private String schema;

    @Value("${data.management.datasource.show-sql}")
    private String showSql;

    @Value("${data.management.datasource.hikari.maximum-pool-size}")
    private int hikariMaximumPoolSize;

    @Value("${data.management.datasource.hikari.minimum-idle}")
    private int minimumIdle;

    @Value("${data.management.datasource.hikari.keepalive-time}")
    private int keepaliveTime;

    @Value("${data.management.datasource.hikari.validation-timeout}")
    private int validationTimeout;

    @Value("${data.management.datasource.hikari.idle-timeout}")
    private int idleTimeout;


    @Value("${data.management.datasource.hikari.max-lifetime}")
    private int maxLifeTime;


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(hikariConfig());
        em.setPackagesToScan("com.devsu.querydatamanagement.infraestructure.dbcustomeradapter");
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("spring.jpa.database-platform", databasePlatform);
        properties.put("hibernate.dialect", databasePlatform);
        properties.put("spring.jpa.show-sql", showSql);
        properties.put("hibernate.show_sql", showSql);
        em.setJpaPropertyMap(properties);
        return em;
    }



    @Bean
    public HikariDataSource hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(urlDatabase);
        hikariConfig.setUsername(usernameDatabase);
        hikariConfig.setPassword(passwordDatabase);
        hikariConfig.setMaximumPoolSize(hikariMaximumPoolSize);
        hikariConfig.setMinimumIdle(minimumIdle);
        hikariConfig.setMaxLifetime(maxLifeTime);
        hikariConfig.setSchema(schema);
        hikariConfig.setKeepaliveTime(keepaliveTime);
        hikariConfig.setValidationTimeout(validationTimeout);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setPoolName("devsuPool");

        return new HikariDataSource(hikariConfig);

    }

    @Bean
    public PlatformTransactionManager primaryTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }
}
