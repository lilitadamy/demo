package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
//	@Bean
//    CommandLineRunner init(UserRepository userRepository) {
//		User user = new User("manager", "manager", "oo",
//				"managerspass", UserRole.USER);
//		userRepository.save(user);
//		return null;
//
//    }

}
