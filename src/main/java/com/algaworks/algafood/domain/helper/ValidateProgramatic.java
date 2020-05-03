package com.algaworks.algafood.domain.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import com.algaworks.algafood.domain.exception.ValidacaoException;

@Component
public class ValidateProgramatic {

	@Autowired
	private SmartValidator validator;

	public void validate(Object target, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, objectName);
		validator.validate(target, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}
}
