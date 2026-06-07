package com.mtitek.spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
