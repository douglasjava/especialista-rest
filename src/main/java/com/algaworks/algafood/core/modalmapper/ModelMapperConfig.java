package com.algaworks.algafood.core.modalmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.v1.model.EnderecoModel;
import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		/* Configurando mapeamento para atributos com nomes diferentes
		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		return modelMapper;
		*/

		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				scr -> scr.getCidade().getEstado().getNome(),
				(destination, value) -> destination.getCidade().setEstado(value));

		//skip - significa ignora esse atributo - ItemPedido::setId
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
				.addMappings(mapping -> mapping.skip(ItemPedido::setId));

		return modelMapper;
	}
	
}