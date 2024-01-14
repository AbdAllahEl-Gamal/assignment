package com.assignment.userdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class UserdetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserdetailsApplication.class, args);
	}

}
