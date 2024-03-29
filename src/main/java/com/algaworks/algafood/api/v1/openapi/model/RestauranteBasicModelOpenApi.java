package com.algaworks.algafood.api.v1.openapi.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.v1.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteBasicModel")
@Getter
@Setter
public class RestauranteBasicModelOpenApi {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Baby Bife")
	private String nome;

	@ApiModelProperty(example = "12.00")
	private BigDecimal taxaFrete;

	private CozinhaModel cozinha;

}
