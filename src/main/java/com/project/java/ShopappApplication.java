package com.project.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
//@EntityScan(basePackages = "com.project.java.model")
public class ShopappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopappApplication.class, args);
	}

}
