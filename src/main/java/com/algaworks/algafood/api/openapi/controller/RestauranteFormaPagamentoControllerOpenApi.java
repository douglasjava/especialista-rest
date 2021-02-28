package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses({ 
	@ApiResponse(code = 204, message = "Associação/Desassociação realizada com sucesso"),
	@ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", response = Problem.class),
})
@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento associadas a restaurante")	
	List<FormaPagamentoModel> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Desassociação de restaurante com forma de pagamento")
	void desassociar(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

	@ApiOperation("Associação de restaurante com forma de pagamento")
	void associar(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}