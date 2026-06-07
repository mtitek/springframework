package com.mtitek.spring.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mtitek.spring.model.AppProfile;

@RestController
@RequestMapping(path = "/api/appProfiles", produces = "application/json")
@CrossOrigin(origins = "http://mtitekauthclient:8080")
public class AppProfileController {
	Map<String, AppProfile> appProfiles = new ConcurrentHashMap<>();

	@GetMapping()
	public Iterable<AppProfile> getAppProfiles() {
		return appProfiles.values();
	}

	@GetMapping("/{id}")
	public ResponseEntity<AppProfile> appProfileById(@PathVariable("id") String id) {
		AppProfile appProfile = appProfiles.get(id);
		if (appProfile != null) {
			return new ResponseEntity<AppProfile>(appProfile, HttpStatus.OK);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AppProfile> postAppProfile(@RequestBody AppProfile appProfile) {
		return new ResponseEntity<AppProfile>(appProfiles.put(appProfile.getId(), appProfile), HttpStatus.OK);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<AppProfile> putAppProfileById(@PathVariable("id") String id, @RequestBody AppProfile appProfileParam) {
		AppProfile appProfile = appProfiles.get(id);
		if (appProfile != null) {
			appProfile.setName(appProfileParam.getName());
			appProfile.setRole(appProfileParam.getRole());
			return new ResponseEntity<AppProfile>(appProfiles.put(appProfile.getId(), appProfile), HttpStatus.OK);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@PutMapping(path = "/{id}/{role}")
	public ResponseEntity<AppProfile> putAppProfileByIdRole(@PathVariable("id") String id, @PathVariable("role") String role) {
		AppProfile appProfile = appProfiles.get(id);
		if (appProfile != null) {
			appProfile.setRole(AppProfile.Role.valueOf(role));
			return new ResponseEntity<AppProfile>(appProfiles.put(appProfile.getId(), appProfile), HttpStatus.OK);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}	
	
	@PatchMapping(path = "/{id}")
	public ResponseEntity<AppProfile> patchAppProfile(@PathVariable("id") String id, @RequestBody AppProfile patch) {
		AppProfile appProfile = appProfiles.get(id);
		if (appProfile != null) {
			appProfile.setName(patch.getName());
			appProfile.setRole(patch.getRole());
			return new ResponseEntity<AppProfile>(appProfiles.put(appProfile.getId(), appProfile), HttpStatus.OK);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAppProfile(@PathVariable("id") String id) {
		appProfiles.remove(id);
	}
}
