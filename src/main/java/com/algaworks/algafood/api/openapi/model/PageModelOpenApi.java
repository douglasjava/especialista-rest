package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PageModelOpenApi {

	@ApiModelProperty(example = "10", value = "Quantidade de registros por página")
	private Long tamanho;
	
	@ApiModelProperty(example = "50", value = "Total de registros")
	private Long totalItens;
	
	@ApiModelProperty(example = "5", value = "Total de páginas")
	private Long totalPaginas;
	
	@ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
	private Long numeroPaginaAtual;
	
}
