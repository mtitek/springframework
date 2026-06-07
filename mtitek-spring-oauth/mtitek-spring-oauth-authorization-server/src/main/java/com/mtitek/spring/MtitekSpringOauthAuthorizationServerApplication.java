package com.mtitek.spring;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mtitek.spring.model.AppUser;
import com.mtitek.spring.repository.AppUserRepository;

@SpringBootApplication
public class MtitekSpringOauthAuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtitekSpringOauthAuthorizationServerApplication.class, args);
	}

	@Bean
	public ApplicationRunner saveAppUsers(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			appUserRepository.save(new AppUser("user1", passwordEncoder.encode("pwd1"), "ROLE_ADMIN"));
			appUserRepository.save(new AppUser("user2", passwordEncoder.encode("pwd2"), "ROLE_ADMIN"));
		};
	}
}