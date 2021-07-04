package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
@ApiResponses({ 
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
		@ApiResponse(code = 201, message = "Cozinha cadastrada"),
		@ApiResponse(code = 200, message = "Cozinha atualizada"),
		@ApiResponse(code = 204, message = "Cozinha excluída")
})
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cozinhas com paginação")
	PagedModel<CozinhaModel> listar(Pageable pageable);

	@ApiOperation("Busca uma cozinha por ID")	
	CozinhaModel buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);

	@ApiOperation("Cadastra uma cozinha")
	CozinhaModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) CozinhaInput cozinhaInput);

	@ApiOperation("Atualiza uma cozinha por ID")
	CozinhaModel atualizar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId,
			@ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados") CozinhaInput cozinhaInput);

	@ApiOperation("Exclui uma cozinha por ID")
	void remover(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);
	
}