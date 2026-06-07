package com.mtitek.spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.mtitek.spring.model.AppProfile;

public interface AppProfileRepository extends CrudRepository<AppProfile, String> {
}
