package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoModalAssembler {

    private ModelMapper modelMapper;

    public PedidoResumoModalAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PedidoResumoModel toModel(Pedido pedido){
        return modelMapper.map(pedido, PedidoResumoModel.class);
    }

    public List<PedidoResumoModel> toCollectionModel(Collection<Pedido> pedidos){
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
}
