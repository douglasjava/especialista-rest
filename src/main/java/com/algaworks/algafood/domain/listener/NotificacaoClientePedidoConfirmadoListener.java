package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

/***
 * Classe responsavel por ouvir/observar o evento lançado na entidade Pedido
 * registerEvent(new PedidoConfirmadoEvent(this)); através desse objeto
 * PedidoConfirmadoEvent Esse evento pode ser publicado também com essa
 * implementação ApplicationEventPublisher publisher
 * 
 * nesse caso foi usado para lançar o evento a implementação
 * AbstractAggregateRoot na entidade Pedido
 * 
 * 
 * @EventListener essa anotação irá ouvir o chamado antes mesmo do processo finalizado conforme explicado na classe {FluxoPedidoService}
 * 
 * @TransactionalEventListener Em qual momento especifico da transação os eventos devem ser disparados. 
 * por padrão os eventos só serã chamados após o commit. 
 * 
 * Ficar atento a esse uso pois após o commit não exitirá o rollback em caso de erro no envio de email. 
 * 
 * (phase = TransactionPhase.BEFORE_COMMIT) irá dar rollback se houver erro no processo de e-mail
 * como o envio do e-mail não é uma coisa critica no processo o não envio pode ser ignorado caso de erro.
 * 
 * Uma solução seria realizar esses envio com mensageria para enviar no futuro novamente caso de erro.
 * 
 * @author Marques
 *
 */
@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService envioEmail;
	
	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {

		Pedido pedido = event.getPedido();

		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("emails/pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();

		envioEmail.enviar(mensagem);
	

	}

}
