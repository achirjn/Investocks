package com.investocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement 
public class InveStocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(InveStocksApplication.class, args);
	}

}
