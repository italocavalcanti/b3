package com.b3.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.b3.users.gateway.listener.ListenerFileDataUser;

@SpringBootApplication
@Component
public class UsersApplication {
	
	static ListenerFileDataUser listener;
	
	@Autowired
	public UsersApplication(ListenerFileDataUser listener) {
		UsersApplication.listener = listener;
	}

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
		listener.read();
	}
}
