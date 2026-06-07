package com.mtitek.spring.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppUser {
	private Long id;

	@NotNull
	@Size(min = 3, message = "Name must be at least 3 characters long")
	private String name;

	@Size(min = 1, message = "You must choose at least 1 appProfile")
	private List<AppProfile> appProfiles = new ArrayList<>();

	public void addAppProfile(AppProfile appProfile) {
		this.appProfiles.add(appProfile);
	}
}
