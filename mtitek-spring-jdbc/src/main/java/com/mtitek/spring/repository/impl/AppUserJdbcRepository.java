package com.mtitek.spring.repository.impl;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mtitek.spring.model.AppProfile;
import com.mtitek.spring.model.AppUser;
import com.mtitek.spring.repository.AppUserRepository;

@Repository
public class AppUserJdbcRepository implements AppUserRepository {
	private JdbcOperations jdbcOperations;

	public AppUserJdbcRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	@Transactional
	public AppUser save(AppUser appUser) {
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"insert into AppUser (name) values (?)", Types.VARCHAR);
		pscf.setReturnGeneratedKeys(true);
		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(appUser.getName()));

		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcOperations.update(psc, keyHolder);

		long appUserId = keyHolder.getKey().longValue();
		appUser.setId(appUserId);

		saveAppProfiles(appUserId, appUser.getAppProfiles());

		return appUser;
	}

	private void saveAppProfiles(long appUserId, List<AppProfile> appProfiles) {
		for (AppProfile appProfile : appProfiles) {
			jdbcOperations.update("insert into AppUserProfile (appUserId, appProfileId) values (?, ?)", appUserId,
					appProfile.getId());
		}
	}
}
