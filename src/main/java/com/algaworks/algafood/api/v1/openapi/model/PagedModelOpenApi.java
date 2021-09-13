package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedModelOpenApi<T> {

	private List<T> lista;
	
	@ApiModelProperty(example = "10", value = "Quantidade de registros por página")
	private Long tamanho;
	
	@ApiModelProperty(example = "50", value = "Total de registros")
	private Long totalItens;
	
	@ApiModelProperty(example = "5", value = "Total de páginas")
	private Long totalPaginas;
	
	@ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
	private Long numeroPaginaAtual;
	
}