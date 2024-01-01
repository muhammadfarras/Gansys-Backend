package com.mfm.gansys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class GansysApplication {

	public static void main(String[] args) {
		SpringApplication.run(GansysApplication.class, args);
	}

}
