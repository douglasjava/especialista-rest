package com.algaworks.algafood.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.v1.assembler.FotoProdutoInputDesassembler;
import com.algaworks.algafood.api.v1.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    private FotoProdutoModelAssembler fotoProdutoModelAssembler;
    private CatalogoFotoProdutoService catalogoFotoProdutoService;
    private FotoProdutoInputDesassembler fotoProdutoInputDesassembler;
    private FotoStorageService fotoStorageService;


    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                          @PathVariable Long produtoId,
                                          @RequestPart(required = true) MultipartFile arquivo,
                                          @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        FotoProduto foto = fotoProdutoInputDesassembler.toModel(fotoProdutoInput, restauranteId, produtoId);

        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, fotoProdutoInput.getArquivo().getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoSalva);

    }
    
    @Override
    @GetMapping
    public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
    	
    	FotoProduto fotoSalva = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
    	
    	return fotoProdutoModelAssembler.toModel(fotoSalva);
    }
    
    /**
     * Nesse caso é necessário capturar a exceção, pois o exception handle rederiza um json, e nessa representação
     * é somente jpeg, portanto tratar a exceção local para evitar o handle capturar ela.
     * @param restauranteId
     * @param produtoId
     * @return
     * @throws HttpMediaTypeNotAcceptableException 
     */
    @Override
    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servir(
    		@PathVariable Long restauranteId, 
    		@PathVariable Long produtoId, @RequestHeader("accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
    	
    	try {
			
    		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        	
    		List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
    		MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
    		
    		catalogoFotoProdutoService.verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
    		
    		FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
        	
			if (fotoRecuperada.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();				
				
			} else {

				return ResponseEntity.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));

			}

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		}

    }
    @Override
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
    	catalogoFotoProdutoService.excluir(restauranteId, produtoId);
    	
	}

}
