package com.algaworks.algafood.api.v1.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.dto.ValidateDeepDto;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import com.algaworks.algafood.domain.service.FormaPagamentoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController  implements FormaPagamentoControllerOpenApi {

    private FormaPagamentoRepository formaPagamentoRepository;
    private CadastroFormaPagamentoService cadastroFormaPagamento;
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
    private FormaPagamentoService formaPagamentoService;


	/**
	 * cachePrivate -> Não comparilha cache, armazena apenas no naegador local <p>
	 * cachePublic -> Pode ser compartilhado, é o comportamento padrão <p> 
	 * noCache -> diferente do nome, existe o cache sim, porém é habilitado a validação em
	 * todas as requisições, por exemplo utilizando o ETAG<p> 
	 * noStore -> Esse sim desabilita o Cache<p>
	 * Para utilizar o Deep ETags é necessário desabilitar o ShallowEtagHeaderFilter<p>
	 * 
	 * @return
	 */
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>>  listar(ServletWebRequest request) {    	
    	ValidateDeepDto validate = formaPagamentoService.validateDeepEtags(request);
    	
    	if(validate.isCheckNotModified()) {
    		return null;
    	}
    	
        List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
        CollectionModel<FormaPagamentoModel> formasPagamentosModel =  formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamentos);
                
        return ResponseEntity.ok()
        		//.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS)) 
        		//.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
        		.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
        		//.cacheControl(CacheControl.noCache())
        		//.cacheControl(CacheControl.noStore())
        		.eTag(validate.getEtag())
        		.body(formasPagamentosModel);
    }

    @Override
    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
    	ValidateDeepDto validate = formaPagamentoService.validateDeepEtags(request, formaPagamentoId);
    	
    	if(validate.isCheckNotModified()) {
    		return null;
    	}
    	
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);
        
        return ResponseEntity.ok()
        		.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
        		.body(formaPagamentoModel);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

        formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @Override
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId,
                                         @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);

        return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
    }

    @Override
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamento.excluir(formaPagamentoId);
    }

}
