package com.algaworks.algafood.api.v2.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@ApiModel("CidadeInput")
public class CidadeInputV2 {

	@ApiModelProperty(example = "Contagem", required = true)
    @NotBlank
    private String nomeCidade;

    @ApiModelProperty(value = "ID do estado", example = "1")
    @NotNull
    private Long idEstado;

}