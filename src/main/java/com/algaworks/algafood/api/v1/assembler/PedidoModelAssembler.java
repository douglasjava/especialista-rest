package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	private ModelMapper modelMapper;
	private AlgaLinks algaLinks;
	private AlgaSecurity algaSecurity;

	public PedidoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
		super(PedidoController.class, PedidoModel.class);
		this.modelMapper = modelMapper;
		this.algaLinks = algaLinks;
		this.algaSecurity = algaSecurity;
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);//self
        modelMapper.map(pedido, pedidoModel);
               
        if (algaSecurity.podePesquisarPedidos()) {
        	pedidoModel.add(algaLinks.linkToPedidos("pedidos"));        	
        }
        
        if(algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
        	
        	if (pedido.podeSerConfirmado()) {
    			pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
    		}
    		if (pedido.podeSerCancelado()) {
    			pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
    		}
    		if (pedido.podeSerEntregue()) {
    			pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
    		}
        	
        }
		
        if (algaSecurity.podeConsultarRestaurantes()) {
        	pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));        	
        }
        
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
        	pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));        	
        }
        
        if (algaSecurity.podeConsultarFormasPagamento()) {
        	pedidoModel.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));        	
        }
        
        
        if (algaSecurity.podeConsultarCidades()) {
        	pedidoModel.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));        	
        }
        
        if (algaSecurity.podeConsultarRestaurantes()) {
	        pedidoModel.getItens().forEach(item -> {
	            item.add(algaLinks.linkToProduto(pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
	        });
        }
        
        return pedidoModel;
		
	}

	/*
	public List<PedidoModel> toCollectionModel(Collection<Pedido> pedidos) {
		return pedidos.stream().map(pedido -> toModel(pedido)).collect(Collectors.toList());
	}
	*/

}

