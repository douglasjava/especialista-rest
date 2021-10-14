package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
//@RequestMapping(value = "/cidades", produces = "application/vnd.algafood.v1+json") //Media Type Custumizado
@RequestMapping(value = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	private CidadeRepository cidadeRepository;
	private CadastroCidadeService cadastroCidade;
	private CidadeModelAssembler cidadeModelAssembler;
	private CidadeInputDisassembler cidadeInputDisassembler;

	@CheckSecurity.Cidades.PodeConsultar
	@Deprecated
	@Override
	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		List<Cidade> cidades = cidadeRepository.findAll();

		return cidadeModelAssembler.toCollectionModel(cidades);
			
	}
	
	@CheckSecurity.Cidades.PodeConsultar
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

		return cidadeModelAssembler.toModel(cidade);
	}

	/*
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
		
		Link link = linkTo(methodOn(CidadeController.class).buscar(cidadeModel.getId())).withSelfRel();
		//cidadeModel.add(new Link("http://localhost:8068/cidades/1", IanaLinkRelations.SELF)); //Ele mesmo
		//cidadeModel.add(linkTo(CidadeController.class).slash(cidadeModel.getId()).withSelfRel());
		cidadeModel.add(link);
		
		
		Link linkLista = linkTo(methodOn(CidadeController.class).listar()).withRel("cidades");
		//cidadeModel.add(new Link("http://localhost:8068/cidades", "cidades"));
		//cidadeModel.add(linkTo(CidadeController.class).withRel("cidades"));
		cidadeModel.add(linkLista);
		
		
		Link linkEstado = linkTo(methodOn(EstadoController.class).buscar(cidadeModel.getEstado().getId())).withSelfRel();
		//cidadeModel.getEstado().add(new Link("http://localhost:8068/estados/1"));
		//cidadeModel.getEstado().add(linkTo(EstadoController.class).slash(cidadeModel.getEstado().getId()).withSelfRel());
		cidadeModel.getEstado().add(linkEstado);
		
		return cidadeModel;

	}
	*/

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {

		try {

			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cadastroCidade.salvar(cidade);

			CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

			return cidadeModel;

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cadastroCidade.salvar(cidadeAtual);

			return cidadeModelAssembler.toModel(cidadeAtual);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);

	}

}
