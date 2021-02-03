package com.hindustantimes.SubscriptionPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication  
@EnableJpaRepositories
public class SubscriptionPortalApplication {

	public static void main(String[] args) { 
		SpringApplication.run(SubscriptionPortalApplication.class, args);
	}  

} 
      