package com.messagingclient.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.messagingclient.config","com.messagingclient.controller","com.messagingclient.model"})
@SpringBootApplication
public class MessagingClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingClientApplication.class, args);
	}

}

