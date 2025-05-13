package com.example.cauhoi2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Cauhoi2Application {
	public static void main(String[] args) {
		SpringApplication.run(Cauhoi2Application.class, args);
	}
}