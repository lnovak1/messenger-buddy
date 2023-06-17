package com.novak.neuralresponser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class NeuralResponserApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeuralResponserApplication.class, args);
	}

}
