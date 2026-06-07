package com.mtitek.spring.repository;

import java.util.List;
import java.util.Optional;

import com.mtitek.spring.model.AppProfile;

public interface AppProfileRepository {
	List<AppProfile> findAll();

	Optional<AppProfile> findById(String id);

	void save(AppProfile appProfile);

	void update(AppProfile appProfile);

	void delete(AppProfile appProfile);
}
