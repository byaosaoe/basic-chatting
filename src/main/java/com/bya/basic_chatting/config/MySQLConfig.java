package com.bya.basic_chatting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Value;


import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class MySQLConfig {

    @Value("${spring.datasource.url}") // lombok 어노테이션이 아님에 주의
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    @Primary
    public JpaTransactionManager transactionManager(DataSource datasource) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setDataSource(datasource);
        return manager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean(name = "createChatTransactionManager")
    public PlatformTransactionManager createChatTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(name = "createUserTransactionManager")
    public PlatformTransactionManager createUserTransactionManager(DataSource datasource) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setDataSource(datasource);
        return manager;
    }
}
