package com.caveofprogramming.spring.web.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.caveofprogramming.spring.web.dao.FormValidationGroup;
import com.caveofprogramming.spring.web.dao.Offer;
import com.caveofprogramming.spring.web.service.OffersService;

@Controller
public class OffersController {

	private OffersService offersService;

	@Autowired
	public void setOffersService(OffersService offersService) {
		this.offersService = offersService;
	}

	// Example
	// Passing variables from website
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String showTest(Model model, @RequestParam("id") String id) {
		System.out.println("Id is : " + id);
		return "index";
	}

	@RequestMapping("/create")
	public String createOffer(Model model, Principal principal) {
		Offer offer = null;

		if (principal != null) {
			String username = principal.getName();
			offer = offersService.getOffer(username);
			model.addAttribute("user", principal.getName());
			model.addAttribute("online", true);
		}

		if (offer == null) {
			offer = new Offer();
		}

		model.addAttribute("offer", offer);

		return "createOffer";
	}

	@RequestMapping(value = "/iscreated", method = RequestMethod.POST)
	public String offerCreated(Model model, @Validated(value=FormValidationGroup.class) Offer offer, BindingResult result,
			Principal principal,
			@RequestParam(value = "delete", required = false) String delete) 
	{
		model.addAttribute("user", principal.getName());
		model.addAttribute("online", true);
		
		if (result.hasErrors()) {
			return "createOffer";
		}
		
		if(delete == null)
		{
			String username = principal.getName();
			offer.getUser().setUsername(username);
			offersService.saveOrUpdate(offer);
			return "offerCreated";
		}
		else
		{
			offersService.delete(offer.getId());
			return "offerDeleted";
		}
		
	}

}
