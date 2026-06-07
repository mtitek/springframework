package com.mtitek.spring.repository;

import com.mtitek.spring.model.AppUser;

public interface AppUserRepository {
	AppUser save(AppUser appUser);
}
