package com.mtitek.spring.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class AppProfile {
	private String id;
	private String name;
	private Role role;

	public enum Role {
		USER, ADMIN, SUPPORT;
	}
}
