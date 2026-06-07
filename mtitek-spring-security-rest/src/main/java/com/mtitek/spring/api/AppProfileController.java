package com.mtitek.spring.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtitek.spring.model.AppProfile;
import com.mtitek.spring.model.AppProfile.Role;

@RestController
@RequestMapping(path = "/api/appProfiles", produces = "application/json")
public class AppProfileController {
	@GetMapping()
	public Iterable<AppProfile> getAppProfiles() {
		List<AppProfile> appProfiles = new ArrayList<>();
		appProfiles.add(new AppProfile("1", "AppProfile 1 USER", Role.USER));
		appProfiles.add(new AppProfile("2", "AppProfile 2 ADMIN", Role.ADMIN));
		appProfiles.add(new AppProfile("3", "profile3", Role.SUPPORT));
		return appProfiles;
	}
}
