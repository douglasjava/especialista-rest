package com.algaworks.algafood.core.modalmapper;

import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


		return modelMapper;
	}

}
