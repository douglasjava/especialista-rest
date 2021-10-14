package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
@ApiResponses({ 
	@ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
	@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
	@ApiResponse(code = 200, message = "Grupo atualizado"),
	@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
	@ApiResponse(code = 204, message = "Grupo excluído")
})
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	CollectionModel<GrupoModel> listar();

	@ApiOperation("Busca um grupo por ID")
	GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

	@ApiOperation("Cadastra um grupo")
	@ApiResponses({ @ApiResponse(code = 201, message = "Grupo cadastrado"), })
	GrupoModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInput grupoInput);

	@ApiOperation("Atualiza um grupo por ID")
	GrupoModel atualizar(
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId,
			@ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", required = true) GrupoInput grupoInput);

	@ApiOperation("Exclui um grupo por ID")
	void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

}