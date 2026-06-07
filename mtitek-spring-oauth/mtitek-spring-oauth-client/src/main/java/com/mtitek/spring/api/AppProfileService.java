package com.mtitek.spring.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mtitek.spring.model.AppProfile;

public interface AppProfileService {
	List<AppProfile> getAppProfiles();

	AppProfile getAppProfileById(String id);

	ResponseEntity<AppProfile> getAppProfileResponseEntityById(String id);

	AppProfile postAppProfile(AppProfile appProfile);

	ResponseEntity<AppProfile> postAppProfileResponseEntity(AppProfile appProfile);

	void putAppProfile(AppProfile appProfile);

	void deleteAppProfileById(String id);
}
