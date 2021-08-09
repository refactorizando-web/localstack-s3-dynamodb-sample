package com.refactorizando.sample.localstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class LocalstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalstackApplication.class, args);
	}

}
