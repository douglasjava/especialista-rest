package com.algaworks.algafood.domain.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PayloadMerger {

	/**
	 * Método responsável por recuperar as informações enviadas pelo Client e adicionar no Objeto a ser pesistido Isso será feito via Reflection
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
	public <T> T merge(Map<String, Object> dadosOrigem, T objetoDesino, Class<T> T) {
		ObjectMapper objectMapper = new ObjectMapper();

		Object objetoOrigem = objectMapper.convertValue(dadosOrigem, T);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(T, nomePropriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, objetoOrigem);
			ReflectionUtils.setField(field, objetoDesino, novoValor);
		});

		return objetoDesino;
	}

}
