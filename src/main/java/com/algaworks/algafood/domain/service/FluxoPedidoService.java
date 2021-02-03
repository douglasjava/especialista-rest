package com.algaworks.algafood.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	private PedidoService pedidoService;
	private PedidoRepository repository;
	
	public FluxoPedidoService(PedidoService pedidoService, PedidoRepository repository) {
		this.pedidoService = pedidoService;
		this.repository = repository;

	}

	/**
	 * É necessário incluir o save do repositorio pois ele será responsavel 
	 * para disparar o evento configurado com AbstractAggregateRoot método registerEvent
	 * Agora é necessário criar um listener para observar o disparo e fazer as ações de responsabilidade dele
	 * @param codigoPedido
	 */
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.confirmar();

		/***
		 * O evento vai ser disparado pelo spring momento antes do flush
		 * portanto é necessário ter cuidado pois pode ser enviado o e-mail antes do 
		 * processo ser de fato finalizado, e ocorrer algum erro no descarregar no banco
		 */
		repository.save(pedido);
		
	}

	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
		
		repository.save(pedido);

	}

	@Transactional
	public void entregue(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.entregar();

	}

}
