package com.mtitek.spring.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mtitek.spring.model.AppProfile;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AppProfileRestService implements AppProfileService {
	private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;

	private final RestTemplate restTemplate;

	private final String BASE_URL = "http://mtitekauthresource:8081";
	private final String BASE_PATH = "/api/appProfiles";

	public AppProfileRestService(OAuth2AuthorizedClientService oauth2AuthorizedClientService) {
		this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
		this.restTemplate = new RestTemplate();
		// add interceptor once, but get access token lazily on each request
		this.restTemplate.getInterceptors().add(getAccessTokenInterceptor());
	}

	private ClientHttpRequestInterceptor getAccessTokenInterceptor() {
		return (request, body, execution) -> {
			String accessToken = getAccessToken(); // getAccessToken per request
			if (accessToken != null) {
				request.getHeaders().add("Authorization", "Bearer " + accessToken);
			}
			return execution.execute(request, body);
		};
	}

	private String getAccessToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof OAuth2AuthenticationToken oauth2AuthenticationToken) {
			String registrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
			if (registrationId.equals("mtitek-registration-id")) {
				OAuth2AuthorizedClient client = oauth2AuthorizedClientService.loadAuthorizedClient(registrationId,
						oauth2AuthenticationToken.getName());
				if (client != null && client.getAccessToken() != null) {
					return client.getAccessToken().getTokenValue();
				}
			}
		}

		return null;
	}

	@Override
	public List<AppProfile> getAppProfiles() {
		return Arrays.asList(restTemplate.getForObject(BASE_URL + BASE_PATH, AppProfile[].class));
	}

	@Override
	public AppProfile getAppProfileById(String id) {
		return restTemplate.getForObject(BASE_URL + BASE_PATH + "/{id}", AppProfile.class, id);
	}

	@Override
	public ResponseEntity<AppProfile> getAppProfileResponseEntityById(String id) {
		return restTemplate.getForEntity(BASE_URL + BASE_PATH + "/{id}", AppProfile.class, id);
	}

	@Override
	public AppProfile postAppProfile(AppProfile appProfile) {
		return restTemplate.postForObject(BASE_URL + BASE_PATH, appProfile, AppProfile.class);
	}

	@Override
	public ResponseEntity<AppProfile> postAppProfileResponseEntity(AppProfile appProfile) {
		return restTemplate.postForEntity(BASE_URL + BASE_PATH, appProfile, AppProfile.class);
	}

	@Override
	public void putAppProfile(AppProfile appProfile) {
		restTemplate.put(BASE_URL + BASE_PATH + "/{id}", appProfile, appProfile.getId());
	}

	@Override
	public void deleteAppProfileById(String id) {
		restTemplate.delete(BASE_URL + BASE_PATH + "/{id}", id);
	}
}