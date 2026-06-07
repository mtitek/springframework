package com.mtitek.spring.security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.context.annotation.RequestScope;

import com.mtitek.spring.api.AppProfileRestService;
import com.mtitek.spring.api.AppProfileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
				.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/mtitek-registration-id") // client oauth login page (takes the authorization code and exchanges it for an access token)
						.authorizationEndpoint(authorization -> authorization
								.authorizationRequestRepository(new HttpSessionOAuth2AuthorizationRequestRepository())))
				.exceptionHandling(ex -> ex.authenticationEntryPoint(
						new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/mtitek-registration-id")))
				.oauth2Client(Customizer.withDefaults());
		return http.build();
	}
}
