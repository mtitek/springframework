package com.mtitek.spring.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import com.mtitek.spring.api.AppProfileService;
import com.mtitek.spring.model.AppProfile;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/appProfiles")
@RequiredArgsConstructor
public class AppProfilesController {
	private final AppProfileService appProfileService;

	@GetMapping("/list")
	public String appProfileListForm() {
		return "appProfileListForm";
	}

	@GetMapping("/new")
	public String appProfileNewForm() {
		return "appProfileNewForm";
	}

	@PostMapping(value = "/new")
	public String saveAppProfile(AppProfile appProfile) {
		appProfileService.postAppProfile(appProfile);

		return "redirect:/appProfiles/list";
	}

	@PostMapping(value = "/process/{id}", params = "update")
	public String updateAppProfile(AppProfile appProfile, @PathVariable("id") String id) {
		appProfileService.putAppProfile(appProfile);

		return "redirect:/appProfiles/list";
	}

	@PostMapping(value = "/process/{id}", params = "delete")
	public String deleteAppProfile(AppProfile appProfile, @PathVariable("id") String id) {
		appProfileService.deleteAppProfileById(id);

		return "redirect:/appProfiles/list";
	}

	@ModelAttribute
	public void addAppProfilesToModel(Model model) {
		List<AppProfile> appProfiles = appProfileService.getAppProfiles();

		model.addAttribute("appProfiles", appProfiles);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public String handleHttpClientErrorException(HttpClientErrorException ex, Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("errorStatusCode", ex.getStatusCode());
		return "appProfileErrorForm";
	}
}