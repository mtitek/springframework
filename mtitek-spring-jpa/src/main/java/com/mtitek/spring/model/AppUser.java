package com.mtitek.spring.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 3, message = "Name must be at least 3 characters long")
	private String name;

	@Size(min = 1, message = "You must choose at least 1 appProfile")
	@ManyToMany()
	private List<AppProfile> appProfiles = new ArrayList<>();

	public void addAppProfile(AppProfile appProfile) {
		this.appProfiles.add(appProfile);
	}
}
