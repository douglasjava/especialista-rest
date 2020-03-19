package com.algaworks.algafood.domain.helper;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PayloadMerger {

	/**
	 * Método responsável por recuperar as informações enviadas pelo Client e
	 * adicionar no Objeto a ser pesistido Isso será feito via Reflection
	 * 
	 * <p>
	 * converte os valores do map para o valor do objeto
	 * <p>
	 * <p>
	 * como o atributo é privado é preciso setar para acessivel
	 * <p>
	 * <p>
	 * Seta o valor do atributo com o valor da propriedade.
	 * <p>
	 * 
	 * @param dadosOrigem
	 * @param restauranteDestino
	 * 
	 * @author douglas.dias
	 */
	public <T> T merge(Map<String, Object> dadosOrigem, T objetoDesino, HttpServletRequest request, Class<T> T) {

		ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			Object objetoOrigem = objectMapper.convertValue(dadosOrigem, T);

			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(T, nomePropriedade);
				field.setAccessible(true);
				Object novoValor = ReflectionUtils.getField(field, objetoOrigem);
				ReflectionUtils.setField(field, objetoDesino, novoValor);
			});

			return objetoDesino;

		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
		}
	}

}
