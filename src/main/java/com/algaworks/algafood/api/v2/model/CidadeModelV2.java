package com.algaworks.algafood.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@ApiModel(value = "CidadeModel", description = "Representa uma cidade")
@Setter
@Getter
@Relation(collectionRelation = "cidades")
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

	@ApiModelProperty(value = "ID da cidade", example = "1")
	private Long idCidade;

	@ApiModelProperty(value = "Nome da cidade", example = "Contagem")
	private String nomeCidade;

	@ApiModelProperty(value = "ID do estado", example = "1")
	private Long idEstado;

	@ApiModelProperty(value = "Nome do Estado", example = "Minas Gerais")
	private String nomeEstado;

}
