package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FotoProdutoInputDesassembler {

    private CadastroProdutoService cadastroProdutoService;

    public FotoProdutoInputDesassembler(CadastroProdutoService cadastroProdutoService) {
        this.cadastroProdutoService = cadastroProdutoService;
    }

    public FotoProduto toModel(FotoProdutoInput fotoProdutoInput, Long restauranteId, Long produtoId) {

        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        return foto;
    }

}
