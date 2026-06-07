package com.mtitek.spring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document (collection = "appProfiles")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class AppProfile {
	@Id
	private String id;
	private String name;
	private Role role;

	public enum Role {
		USER, ADMIN, SUPPORT;
	}
}
