package com.caveofprogramming.spring.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidEmailcode implements ConstraintValidator <ValidEmail, String> 
{
	private int min;

	@Override
	public void initialize(ValidEmail constraintAnnotation) 
	{
		min = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) 
	{
		if (email.length() < min)
		{
			return false;
		}
		
		//Using a custom validation algorithm from commons-validator (see jar)
		if(!EmailValidator.getInstance(false).isValid(email))
		{
			return false;
		}
		
		return true;
	}

}
