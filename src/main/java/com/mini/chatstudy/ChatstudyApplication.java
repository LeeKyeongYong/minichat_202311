package com.mini.chatstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChatstudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatstudyApplication.class, args);
	}

}
