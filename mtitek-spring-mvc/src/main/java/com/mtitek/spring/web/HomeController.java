package com.mtitek.spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		// templating (thymeleaf): return the vue name
		// the template name is derived from the logical view name by prefixing it with /templates/ and postfixing it with .html
		// /src/main/resources/templates/home.html
		return "home";
	}

//	@GetMapping("/")
//	public String home() {
//		// serves the static file directly (unit test needs to be addressed)
//		// /src/main/resources/static/home.html
//		return "redirect:/home.html";
//	}
}
