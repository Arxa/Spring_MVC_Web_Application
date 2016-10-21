package com.caveofprogramming.spring.web.controllers;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caveofprogramming.spring.web.dao.Offer;
import com.caveofprogramming.spring.web.dao.User;
import com.caveofprogramming.spring.web.service.OffersService;
import com.caveofprogramming.spring.web.service.UsersService;



@Controller
public class HomeController 
{
	private static Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private OffersService offersService;
	
	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/")
	public String showIndex(Model model, Principal principal)
	{
		List<Offer> offers = offersService.getCurrent();
		model.addAttribute("offers", offers);
	
		boolean hasOffer = false;
		
		if (principal != null) // user is logged in
		{
			model.addAttribute("user", principal.getName());
			model.addAttribute("online", true);
			hasOffer = offersService.hasOffer(principal.getName());
		}
		
		model.addAttribute("hasOffer", hasOffer);
		
		return "index";
	}	
}
