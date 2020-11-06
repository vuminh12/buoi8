package com.example.demospringbean.Model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(
        entityManagerFactoryRef = "customersEntityManager",
        transactionManagerRef = "customersTransactionManager",
        basePackages = {"com.example.demospringbean.Repository"})
public class AppConfig {
    @Autowired
    private Environment environment;

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "spring.datasource.driverClassName";
    private static final String PROPERTY_NAME_DATABASE_URL = "spring.datasource.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "spring.datasource.username";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "spring.datasource.password";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "spring.jpa.hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_HBN2DDL_SQL = "spring.jpa.hibernate.ddl-auto";


    @Bean(name = "customersDataSource")
    @Primary
    public DataSource getDataSourceMysql() {
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.url(environment.getProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.username(environment.getProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.password(environment.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        dataSource.driverClassName(environment.getProperty(PROPERTY_NAME_DATABASE_DRIVER));
        return dataSource.build();
    }

    @Bean(name = "customersEntityManager")
    public LocalContainerEntityManagerFactoryBean getCustomersEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("customersDataSource") DataSource customersDataSource){
        // config bean entity của data base tương đương với bảng, config jpa
        LocalContainerEntityManagerFactoryBean result = builder
                .dataSource(customersDataSource)
                .packages("com.example.demospringbean.Model")
                .persistenceUnit("customers")
                .properties(additionalJpaProperties())
                .build();
        return result;
    }

    Map<String,?> additionalJpaProperties(){
        Map<String,String> map = new HashMap<>();
        map.put(PROPERTY_NAME_HIBERNATE_HBN2DDL_SQL, environment.getProperty(PROPERTY_NAME_HIBERNATE_HBN2DDL_SQL));
        map.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        return map;
    }

    @Bean(name = "customersTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("customersEntityManager") EntityManagerFactory customersEntityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(customersEntityManager);
        return transactionManager;
    }
}
