package com.mtitek.spring.rest.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mtitek.spring.model.AppProfile;
import com.mtitek.spring.model.AppProfile.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppProfileRestClient {

	@Autowired
	private RestTemplate restTemplate;

	private final String BASE_URL = "http://localhost:8080";
	private final String BASE_PATH = "/api/appProfiles";

	@Bean
	public CommandLineRunner getAppProfiles() {
		return args -> {
			try {
				List<AppProfile> appProfiles = restTemplate.exchange(BASE_URL + BASE_PATH, HttpMethod.GET, null,
						new ParameterizedTypeReference<List<AppProfile>>() {
						}).getBody();
				log.info("getAppProfiles: " + appProfiles);
			} catch (HttpClientErrorException.NotFound ex) {
				log.error(ex.getMessage(), ex);
			} catch (HttpClientErrorException ex) {
				log.error(ex.getMessage(), ex);
			}
		};
	}	
	
	@Bean
	public CommandLineRunner getAppProfileById() {
		return args -> {
			try {
				AppProfile appProfile = restTemplate.getForObject(BASE_URL + BASE_PATH + "/{id}", AppProfile.class, 1);
				log.info("getAppProfileById: " + appProfile);
			} catch (HttpClientErrorException.NotFound ex) {
				log.error(ex.getMessage(), ex);
			} catch (HttpClientErrorException ex) {
				log.error(ex.getMessage(), ex);
			}
		};
	}

	@Bean
	public CommandLineRunner getAppProfileResponseEntityById() {
		return args -> {
			try {
				ResponseEntity<AppProfile> responseEntity = restTemplate.getForEntity(BASE_URL + BASE_PATH + "/{id}",
						AppProfile.class, 1);
				log.info("getAppProfileResponseEntityById: " + responseEntity);
			} catch (HttpClientErrorException.NotFound ex) {
				log.error(ex.getMessage(), ex);
			} catch (HttpClientErrorException ex) {
				log.error(ex.getMessage(), ex);
			}
		};

	}

	@Bean
	public CommandLineRunner putAppProfile() {
		return args -> {
			try {
				AppProfile appProfile = new AppProfile("1", "AppProfile 1 ADMIN", Role.ADMIN);
				restTemplate.put(BASE_URL + BASE_PATH + "/{id}", appProfile, appProfile.getId());
				log.info("putAppProfile: " + appProfile);
			} catch (HttpClientErrorException ex) {
				log.error(ex.getMessage(), ex);
			}
		};
	}

	@Bean
	public CommandLineRunner deleteAppProfileById() {
		return args -> {
			try {
				restTemplate.delete(BASE_URL + BASE_PATH + "/{id}", 3);
				log.info("deleteAppProfileById: " + 3);
			} catch (HttpClientErrorException.NotFound ex) {
				log.error(ex.getMessage(), ex);
			} catch (HttpClientErrorException ex) {
				log.error(ex.getMessage(), ex);
			}
		};
	}
}
