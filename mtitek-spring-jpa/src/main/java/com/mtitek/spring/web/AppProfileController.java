package com.mtitek.spring.web;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mtitek.spring.model.AppProfile;
import com.mtitek.spring.model.AppProfile.Role;
import com.mtitek.spring.model.AppUser;
import com.mtitek.spring.repository.AppProfileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/appProfiles")
@SessionAttributes("appUser")
public class AppProfileController {
	private final AppProfileRepository appProfileRepository;

	// @Autowired
	public AppProfileController(AppProfileRepository appProfileRepository) {
		this.appProfileRepository = appProfileRepository;
	}

	@GetMapping
	public String appProfileForm() {
		return "appProfileForm";
	}

	@PostMapping
	public String addAppProfile(@RequestParam(required = false) String appProfileId, @ModelAttribute AppUser appUser,
			Model model) {
		if (appProfileId == null || appProfileId.isEmpty()) {
			model.addAttribute("errorMessage", "Empty profile id");
			return "appProfileForm";
		}

		Optional<AppProfile> appProfile = appProfileRepository.findById(appProfileId);

		if (appProfile.isEmpty()) {
			model.addAttribute("errorMessage", "Invalid profile id: " + appProfileId);
			return "appProfileForm";
		}

		appUser.addAppProfile(appProfile.get());

		return "redirect:/appUsers/user";
	}

	@ModelAttribute
	public void addAppProfilesToModel(Model model) {
		Iterable<AppProfile> appProfiles = appProfileRepository.findAll();

		Role[] roles = AppProfile.Role.values();
		for (Role role : roles) {
			model.addAttribute(role.toString().toLowerCase(), filterByRole(appProfiles, role));
		}
	}

	@ModelAttribute(name = "appUser")
	public AppUser appUser() {
		return new AppUser();
	}

	private Iterable<AppProfile> filterByRole(Iterable<AppProfile> appProfiles, Role role) {
		return StreamSupport.stream(appProfiles.spliterator(), false).filter(x -> x.getRole().equals(role))
				.collect(Collectors.toList());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "appProfileForm";
	}
}
