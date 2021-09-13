package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
@ApiResponses({ 
		@ApiResponse(code = 201, message = "Cidade Cadastrada/Atualizada com sucesso"),
		@ApiResponse(code = 204, message = "Cidade excluída com sucesso"),
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
public interface CidadeControllerV2OpenApi {

	@ApiOperation("Lista as cidades")
	CollectionModel<CidadeModelV2> listar();

	
	@ApiOperation("Busca uma cidade por ID")
	CidadeModelV2 buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

	
	@ApiOperation("Cadastra uma cidade")
	CidadeModelV2 adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInputV2 cidadeInput);

	
	@ApiOperation("Atualiza uma cidade por ID")	
	CidadeModelV2 atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
			Long cidadeId,
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
			CidadeInputV2 cidadeInput);
	

	@ApiOperation("Exclui uma cidade por ID")	
	void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

}
