package com.mtitek.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mtitek.spring.model.AppProfile;
import com.mtitek.spring.model.AppProfile.Role;
import com.mtitek.spring.repository.AppProfileRepository;

@SpringBootApplication
public class MtitekSpringMongodbApplication {
	public static void main(String[] args) {
		SpringApplication.run(MtitekSpringMongodbApplication.class, args);
	}

	@Bean
	public CommandLineRunner saveAppProfiles(AppProfileRepository repo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				repo.save(new AppProfile("1", "AppProfile 1 USER", Role.USER));
				repo.save(new AppProfile("2", "AppProfile 2 ADMIN", Role.ADMIN));
				repo.save(new AppProfile("3", "AppProfile 3 SUPPORT", Role.SUPPORT));
			}
		};
	}
}
