package com.example.RealConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RealConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealConnectApplication.class, args);
	}

}
