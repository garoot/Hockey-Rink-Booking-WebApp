package com.rinkbooking;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class HomeController {
	
	//returns home page
	@RequestMapping("/")
	public String showPage() {
		return "main-menu";
		
	}

}
