package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.v1.openapi.model.RestauranteBasicModelOpenApi;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cidade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@ApiResponses({ 
	@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
	@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
	@ApiResponse(code = 201, message = "Restaurante cadastrado"),
	@ApiResponse(code = 200, message = "Restaurante atualizado"),
	@ApiResponse(code = 204, message = "Restaurante ativado com sucesso")
})
@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicModelOpenApi.class)
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nome da projeção de pedidos", 
					allowableValues = "apenas-nome", 
					name = "projecao", paramType = "query", type = "string") 
	})
	//@JsonView(RestauranteView.Resumo.class)
	CollectionModel<RestauranteBasicoModel> listar();

	@ApiIgnore
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

	@ApiOperation("Busca um restaurante por ID")	
	RestauranteModel buscar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Cadastra um restaurante")
	RestauranteModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);

	@ApiOperation("Atualiza um restaurante por ID")
	RestauranteModel atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", required = true) RestauranteInput restauranteInput);

	@ApiOperation("Remover um restaurante por ID")
	ResponseEntity<Cidade> remover(@ApiParam(value = "ID de um restaurante", example = "1", required = true) @PathVariable Long restauranteId);	
	
	@ApiOperation("Ativa um restaurante por ID")
	ResponseEntity<Void> ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Inativa um restaurante por ID")
	ResponseEntity<Void> inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Ativa múltiplos restaurantes")
	ResponseEntity<Void> ativarMultiplos(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Inativa múltiplos restaurantes")
	ResponseEntity<Void> inativarMultiplos(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Abre um restaurante por ID")
	ResponseEntity<Void> abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Fecha um restaurante por ID")
	ResponseEntity<Void> fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

}