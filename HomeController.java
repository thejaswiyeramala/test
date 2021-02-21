package com.wf.ibs.bootappsecure.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



//Shall contain processing logic
//Bean created
//Register with Handler Mapper
@Controller
public class HomeController {

	
	@RequestMapping("/custom-login")
	public String customLogin() {
		/*IBSLogin ibsLogin = new IBSLogin();
		model.addAttribute("ibsLogin", ibsLogin);
		return "ibs-login";*/
		//return "redirect:/customer/login";
		return  "login-form";
	}
	
	// to respond to root URL
	@RequestMapping("/")
	public String home() {
		// add business logic
		
		// respond back with view page name
		return  "index";
	}
			
	@RequestMapping("/access-denied")
	public String accessDenied() {
		
		return "invalid-details";
	}
}
