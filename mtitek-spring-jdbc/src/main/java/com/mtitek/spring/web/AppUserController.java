package com.mtitek.spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mtitek.spring.model.AppUser;
import com.mtitek.spring.repository.AppUserRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/appUsers")
@SessionAttributes("appUser")
public class AppUserController {
	private AppUserRepository appUserRepository;

	public AppUserController(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	@GetMapping("/user")
	public String appUserForm() {
		return "appUserForm";
	}

	@PostMapping
	public String processAppUser(@Valid AppUser appUser, Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "appUserForm";
		}

		appUserRepository.save(appUser);
		sessionStatus.setComplete();

		return "redirect:/appProfiles/appUsers";
	}
}
