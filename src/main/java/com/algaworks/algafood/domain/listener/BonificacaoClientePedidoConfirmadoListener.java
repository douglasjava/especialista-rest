package com.algaworks.algafood.domain.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BonificacaoClientePedidoConfirmadoListener {

	@EventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		log.info("Pontos calculados para o cliente {} ", pedido.getCliente().getNome());
	}
	
}
