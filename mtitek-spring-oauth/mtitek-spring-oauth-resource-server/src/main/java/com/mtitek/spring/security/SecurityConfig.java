package com.mtitek.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(1) // REST API SecurityFilterChain — evaluated first
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/api/**") // applies to /api/** request
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/api/appProfiles/**").hasAuthority("SCOPE_addAppProfile")
						.requestMatchers(HttpMethod.PUT, "/api/appProfiles/**").hasAuthority("SCOPE_updateAppProfile")
						.requestMatchers(HttpMethod.PATCH, "/api/appProfiles/**").hasAuthority("SCOPE_updateAppProfile")
						.requestMatchers(HttpMethod.DELETE, "/api/appProfiles/**").hasAuthority("SCOPE_deleteAppProfile")
						.anyRequest().permitAll())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) // uses jwt token and check it includes the requested scopes
				.httpBasic(Customizer.withDefaults()) // uses HTTP Basic auth
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // no session/cookies
				.csrf(csrf -> csrf.disable()) // no need for CSRF protection for REST APIs
				.build();
	}

	@Bean
	@Order(2) // Web APP SecurityFilterChain — evaluated second (fallback)
	public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/appProfiles/**").hasRole("USER").anyRequest().permitAll())
				.formLogin(form -> form.loginPage("/login"))
				.logout(logout -> logout.logoutSuccessUrl("/"))
				// disabling csrf for REST API: 403 forbidden
				.csrf(csrf -> csrf.ignoringRequestMatchers("/api/appProfiles/**"))
				// allow pages to be loaded in frames from the same origin
				.headers(headers -> headers.frameOptions(frameoptions -> frameoptions.sameOrigin()))
				.build();
	}
}
