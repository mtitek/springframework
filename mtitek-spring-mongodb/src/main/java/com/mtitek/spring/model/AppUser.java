package com.mtitek.spring.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document (collection = "appUsers")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AppUser {
	@Id
	private String id;

	@NotNull
	@Size(min = 3, message = "Name must be at least 3 characters long")
	private String name;

	@Size(min = 1, message = "You must choose at least 1 appProfile")
	private List<AppProfile> appProfiles = new ArrayList<>();

	public void addAppProfile(AppProfile appProfile) {
		this.appProfiles.add(appProfile);
	}
}
