package com.algaworks.algafood.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
public abstract class BaseIdInput {

	@ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;

}
