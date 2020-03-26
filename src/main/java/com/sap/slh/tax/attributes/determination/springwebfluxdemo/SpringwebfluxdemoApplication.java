package com.sap.slh.tax.attributes.determination.springwebfluxdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan("com.sap.slh.tax.*")
@SpringBootApplication
@EnableAsync
public class SpringwebfluxdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringwebfluxdemoApplication.class, args);
	}

}
