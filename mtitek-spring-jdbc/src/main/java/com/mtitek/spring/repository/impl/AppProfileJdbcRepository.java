package com.mtitek.spring.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mtitek.spring.model.AppProfile;
import com.mtitek.spring.repository.AppProfileRepository;

@Repository
public class AppProfileJdbcRepository implements AppProfileRepository {
	private JdbcTemplate jdbcTemplate;
	private JdbcOperations jdbcOperations;

	// When there's only one constructor, spring implicitly applies autowiring of dependencies through the constructor's parameters.
	// @Autowired
	public AppProfileJdbcRepository(JdbcTemplate jdbcTemplate, JdbcOperations jdbcOperations) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	public List<AppProfile> findAll() {
		return jdbcTemplate.query("select id, name, role from AppProfile", this::mapRowToAppProfile);
	}

	@Override
	public Optional<AppProfile> findById(String id) {
		List<AppProfile> results = jdbcTemplate.query("select id, name, role from AppProfile where id=?",
				this::mapRowToAppProfile, id);
		return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
	}

	private AppProfile mapRowToAppProfile(ResultSet row, int rowNum) throws SQLException {
		return new AppProfile(row.getString("id"), row.getString("name"),
				AppProfile.Role.valueOf(row.getString("role")));
	}

	@Override
	public void save(AppProfile appProfile) {
		jdbcOperations.update("insert into AppProfile (id, name, role) values (?, ?, ?)", appProfile.getId(),
				appProfile.getName(), appProfile.getRole().name());
	}

	@Override
	public void update(AppProfile appProfile) {
		jdbcOperations.update("update AppProfile set name=?, role=? where id=?", appProfile.getName(),
				appProfile.getRole().name(), appProfile.getId());
	}

	@Override
	public void delete(AppProfile appProfile) {
		jdbcOperations.update("delete from AppProfile where id = ?", appProfile.getId());
	}
}
