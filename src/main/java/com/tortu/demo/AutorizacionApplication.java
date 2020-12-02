package com.tortu.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class AutorizacionApplication {

	public static void main(String[] args) {
		log.info("corriendo autorization service");
		SpringApplication.run(AutorizacionApplication.class, args);

	}

}
