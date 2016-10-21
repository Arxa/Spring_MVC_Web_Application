package com.caveofprogramming.spring.web.controllers;


import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorHandler 
{
	@ExceptionHandler(DataAccessException.class)
	public String handleDatabaseException(DataAccessException ex)
	{
		ex.printStackTrace();
		return "error";
	}
	
	
	// What would you like to do if you catch and AccessDeniedException?
	@ExceptionHandler()
	public String handleDatabaseException(AccessDeniedException ex)
	{
		return "denied";
	}

}
