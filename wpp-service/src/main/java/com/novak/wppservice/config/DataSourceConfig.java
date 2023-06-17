package com.novak.wppservice.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

//    TODO: config for db connection here
//    @Bean
//    public DataSource getDataSource(){
//        return DataSourceBuilder.create().
//                driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/myDb")
//                .username("user1")
//                .password("pass")
//                .build();
//    }
}
