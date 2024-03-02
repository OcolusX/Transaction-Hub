package com.TransactionHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionHubApplication.class, args);
	}

}
