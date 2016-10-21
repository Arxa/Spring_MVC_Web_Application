package com.caveofprogramming.spring.web.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caveofprogramming.spring.web.dao.FormValidationGroup;
import com.caveofprogramming.spring.web.dao.Message;
import com.caveofprogramming.spring.web.dao.Offer;
import com.caveofprogramming.spring.web.dao.User;
import com.caveofprogramming.spring.web.service.UsersService;

@Controller
public class LoginController 
{
	private UsersService usersService;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	
	@RequestMapping("/denied")
	public String showDenied()
	{
		return "denied";
	}
	
	@RequestMapping("/messages")
	public String showMessages(Model model, Principal principal)
	{
		model.addAttribute("user", principal.getName());
		model.addAttribute("online", true);
		return "messages";
	}
	
	@RequestMapping("/admin")
	public String showAdmin(Model model)
	{
		List<User> users = usersService.getAllUsers();
		model.addAttribute("users", users);
		return "admin";
	}
	
	@RequestMapping("/login")
	public String showLogin()
	{
		return "loginForm";
	}
	
	@RequestMapping("/loginForm")
	public String showLoginForm()
	{
		return "loginForm";
	}
	

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String showLogout(HttpServletRequest request, HttpServletResponse response)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/loggedOut";
	}
	
	@RequestMapping("/loggedOut")
	public String showLoggedOut()
	{
		return "loggedOut";
	}
	
	@RequestMapping("/newAccount")
	public String showNewAccount(Model model)
	{
		model.addAttribute("user", new User());
		return "newAccount";
	}
	
	@RequestMapping("/register")
	public String showRegister(Model model)
	{
		model.addAttribute("user", new User());
		return "newAccount";
	}
	
	
	@RequestMapping(value="/createAccount", method=RequestMethod.POST)
	public String createAccount(@Validated(FormValidationGroup.class) User user, BindingResult result) // With @Validated, we are stating the group of validation that we want to use for User(Front-End Forms). 
	{
		if (result.hasErrors())
		{
			return "newAccount";	
		}
		
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		
		if(usersService.exists(user.getUsername()))
		{
			result.rejectValue("username", "DuplicatedKey.user.username");
			return "newAccount";
		}
		
		try 
		{
			usersService.create(user);
		} 
		catch (DuplicateKeyException e) 
		{
			result.rejectValue("username", "DuplicatedKey.user.username");
			return "newAccount";
		}
		
		return "accountCreated";
	}
	
	@RequestMapping(value="/getMessages", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> getMessages(Principal principal)
	{
		List<Message> messages = null;
		if(principal == null)
		{
			messages = new ArrayList<Message>();
		}
		else
		{
			String username = principal.getName();
			messages = usersService.getMessages(username);
		}
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("messages", messages);
		data.put("number", messages.size());

		return data;
	}
	
	
	@RequestMapping(value="/sendMessage", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendMessages(Principal principal, @RequestBody Map<String, Object> data)
	{
		String text = (String)data.get("text");
		String name = (String)data.get("name");
		String email = (String)data.get("email");
		Integer target = (Integer)data.get("target");
		
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("nikarchakis@gmail.com");
		mail.setTo(email);
		mail.setSubject("Re: " + name + "You got a new message!");
		mail.setText(text);
		
		try
		{
			mailSender.send(mail);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Cannot send email");
		}
		
		Map<String, Object> rval = new HashMap<String, Object>();
		rval.put("success", true);
		rval.put("target", target);

		return rval;
	}
}
