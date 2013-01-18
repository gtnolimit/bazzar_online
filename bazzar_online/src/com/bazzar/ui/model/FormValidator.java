package com.bazzar.ui.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * @author SivaLabs
 *
 */
@Component("contactFormValidator")
public class FormValidator implements Validator
{
	@Override
	public boolean supports(Class<?> clazz)
	{
		return true;
		//return Contact.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors)
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","required.name", "Name is required.");
	}

}
