package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Setter
@Getter
@Relation(collectionRelation = "cidades")
public class CidadeModel extends RepresentationModel<CidadeModel> {

	@ApiModelProperty(value = "ID da cidade", example = "1")
	private Long id;

	@ApiModelProperty(value = "Nome da cidade", example = "Contagem")
	private String nome;

	private EstadoModel estado;

}
