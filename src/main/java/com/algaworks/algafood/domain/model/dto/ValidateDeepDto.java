package com.algaworks.algafood.domain.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidateDeepDto {

	private boolean checkNotModified;
	private String etag;

}
