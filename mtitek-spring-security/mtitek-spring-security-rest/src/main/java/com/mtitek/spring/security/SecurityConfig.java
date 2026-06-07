package com.mtitek.spring.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.mtitek.spring.model.AppUser;

@Configuration
public class SecurityConfig {
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		List<UserDetails> userList = new ArrayList<>();
		userList.add(new AppUser("user1", encoder.encode("pwd1")));
		userList.add(new AppUser("user2", encoder.encode("pwd2")));
		return new InMemoryUserDetailsManager(userList);
	}

	@Bean
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/api/**") // applies to /api/** request
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/appProfiles/**").hasRole("USER").anyRequest().permitAll())
				.httpBasic(Customizer.withDefaults()) // uses HTTP Basic auth
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // no session/cookies
				.csrf(csrf -> csrf.disable()) // no need for CSRF protection for REST APIs
				.build();
	}
}
