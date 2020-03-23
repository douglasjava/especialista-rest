package com.algaworks.algafood.domain.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class ValidateProgramatic {

	@Autowired
	private SmartValidator validator;

	public void validate(Restaurante object, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(object, objectName);
		validator.validate(object, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}
}
