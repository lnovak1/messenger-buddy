package com.novak.wppservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
// TODO: until jdbc drivers ready we need exclude , remove after config db
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class WppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WppServiceApplication.class, args);
	}

}
