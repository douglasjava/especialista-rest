package com.algaworks.algafood.domain.model;

import lombok.Getter;

@Getter
public enum RestauranteProjecao {

    COMPLETO("completo"),
    RESUMO("resumo"),
    SIMPLES("simples");

    private String descricao;

    RestauranteProjecao(String descricao) {
        this.descricao = descricao;
    }

}
