package com.mtitek.spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.mtitek.spring.model.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
	AppUser findByUsername(String username);
}
