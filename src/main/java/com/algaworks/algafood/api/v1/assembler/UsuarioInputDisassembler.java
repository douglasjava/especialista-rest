package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {

    private ModelMapper modelMapper;

    public UsuarioInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}