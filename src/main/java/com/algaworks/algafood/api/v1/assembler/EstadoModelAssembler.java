package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    private ModelMapper modelMapper;
	private AlgaLinks algaLinks;
	private AlgaSecurity algaSecurity;

    public EstadoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
    	super(EstadoController.class, EstadoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public EstadoModel toModel(Estado estado) {
    	EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);
        
        if (algaSecurity.podeConsultarEstados()) {
        	estadoModel.add(algaLinks.linkToEstados("estados"));        	
        }
        
        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoModel> collectionModel = super.toCollectionModel(entities);
        
        if (algaSecurity.podeConsultarEstados()) {
            collectionModel.add(algaLinks.linkToEstados());
        }
        
        return collectionModel;
    }
    
    
    /*
    public List<EstadoModel> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(estado -> toModel(estado))
                .collect(Collectors.toList());
    }
    */

}
