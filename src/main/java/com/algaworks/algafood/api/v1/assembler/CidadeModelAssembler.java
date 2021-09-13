package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    private ModelMapper modelMapper;
    private AlgaLinks algaLinks;

    public CidadeModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
    	super(CidadeController.class, CidadeModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
    	CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
    	modelMapper.map(cidade, cidadeModel);
    	
    	//CidadeModel cidadeModel = modelMapper.map(cidade, CidadeModel.class);    	
    	//cidadeModel.add(linkTo(methodOn(CidadeController.class).buscar(cidadeModel.getId())).withSelfRel());
    	
    	cidadeModel.add(algaLinks.linkToCidades("cidades"));        
        cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
    	
    	return cidadeModel;
    }
    
    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
    	return super.toCollectionModel(entities).add(algaLinks.linkToCidades());
    }

    /*
    public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }
    */

}