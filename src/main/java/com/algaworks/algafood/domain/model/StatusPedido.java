package com.algaworks.algafood.domain.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum StatusPedido {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    private String descricao;
    private List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAteriores) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAteriores);
    }

    public boolean isCriado() {
        return CRIADO.equals(this);
    }

    public boolean isDiferenteCriado() {
        return !isCriado();
    }

    public boolean isConfirmado() {
        return CONFIRMADO.equals(this);
    }

    public boolean isDiferenteConfirmado() {
        return !isConfirmado();
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
        return !novoStatus.statusAnteriores.contains(this);
    }
}
