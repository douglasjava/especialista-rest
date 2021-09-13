package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    private ModelMapper modelMapper;
    private AlgaLinks algaLinks;

    public PedidoResumoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
    	super(PedidoController.class, PedidoResumoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;        
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido){
    	PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
        
        pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
        
        return pedidoModel;
    }

    /*
    public List<PedidoResumoModel> toCollectionModel(Collection<Pedido> pedidos){
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
    */
}
