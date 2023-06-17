package com.novak.msgconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class MsgConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgConsumerApplication.class, args);
	}

}
